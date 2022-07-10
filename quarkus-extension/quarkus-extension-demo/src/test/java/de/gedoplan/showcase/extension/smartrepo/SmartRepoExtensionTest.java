package de.gedoplan.showcase.extension.smartrepo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;

//@QuarkusTest
public class SmartRepoExtensionTest {

//  @Test
  public void testGreeting() {
    RestAssured
      .when()
      .get("/showcase/smartrepo")
      .then()
      .statusCode(200)
      .body(containsString("Hello"));
  }

}