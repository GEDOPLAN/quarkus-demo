package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Planet;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class PlanetResource2Test {
  public static final String PATH = "/v2/planets";

  @Test
  public void test_01_DagobertAndDonalDuckExist() {
    List<Planet> planets = given()
        .when()
        .get(PATH)
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
        .post(PATH)
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
