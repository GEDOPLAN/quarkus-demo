package de.gedoplan.showcase.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.gedoplan.showcase.entity.Planet;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@QuarkusTest
public class PlanetResourceTest {

  @ParameterizedTest
  @MethodSource("provideApiContexts")
  public void test_01_EarthAndJupiterExist(String path) {
    List<Planet> planets = given()
        .when()
        .get(path)
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

  @ParameterizedTest
  @MethodSource("provideApiContexts")
  public void test_02_PostGetDelete(String path) throws Exception {
    Planet planet = new Planet("X-" + UUID.randomUUID().toString(), 0);

    String newPlanetUrl = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(planet)
        .post(path)
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

  private static Stream<Arguments> provideApiContexts() {
    return Stream.of(
      Arguments.of("/v1/planets"),
      Arguments.of("/v2/planets")
    );
  }
}
