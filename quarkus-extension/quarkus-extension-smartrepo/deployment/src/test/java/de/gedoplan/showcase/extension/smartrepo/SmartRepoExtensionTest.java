package de.gedoplan.showcase.extension.smartrepo;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.hamcrest.Matchers.containsString;

public class SmartRepoExtensionTest {

  @RegisterExtension
  static final QuarkusUnitTest config = new QuarkusUnitTest().withEmptyApplication();

  @Test
  public void testSmartRepo() {
    RestAssured.when().get("/showcase/smartrepo").then().statusCode(200).body(containsString("Hello"));
  }

}