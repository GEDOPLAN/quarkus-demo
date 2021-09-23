package de.gedoplan.showcase.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DemoEndpointTest {

  @Test
  public void testGetGreeting() {
    given()
        .when().get("/demo/greeting")
        .then()
        .statusCode(200);
  }

  @Test
  public void testgetCardCheck() {
    given()
        .when().get("/demo/card-check?cardNo=4000000000000")
        .then()
        .statusCode(200)
        .body(is("valid"));
  }

}