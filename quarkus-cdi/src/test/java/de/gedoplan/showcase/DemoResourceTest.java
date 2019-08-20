package de.gedoplan.showcase;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DemoResourceTest {

  @Test
  public void testGetGreeting() {
    given()
        .when().get("/demo/greeting")
        .then()
        .statusCode(200);
  }

}