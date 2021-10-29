package de.gedoplan.showcase.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.gedoplan.showcase.entity.Planet;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;

@QuarkusTest
public class PlanetEndpointTest {

  @Test
  public void test_01_DagobertAndDonalDuckExist() {
    List<Planet> planets = given()
        .when()
        .get("/planets")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(new TypeRef<List<Planet>>() {});

    boolean foundEarth = false;
    boolean foundJupiter = false;

    for (Planet planet : planets) {
      if ("earth".equals(planet.getName())) {
        foundEarth = true;
      }
      if ("jupiter".equals(planet.getName())) {
      foundJupiter = true;
      }
    }

    assertTrue(foundEarth, "Earth not found");
    assertTrue(foundJupiter, "Jupiter not found");
  }

  @Test
  public void test_02_PostGetDelete() throws Exception {
    Planet planet = new Planet("X-" + UUID.randomUUID().toString(), 0);

    String newPlanetUrl = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(planet)
        .post("/planets")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .header(HttpHeaders.LOCATION);

    assertNotNull(newPlanetUrl, "New planet URL must not be null");

    given()
        .when()
        .get(newPlanetUrl)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("name", is(planet.getName()));

    given()
        .when()
        .delete(newPlanetUrl)
        .then()
        .statusCode(HttpStatus.SC_NO_CONTENT);

    given()
        .when()
        .get(newPlanetUrl)
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

}
