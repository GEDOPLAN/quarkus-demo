package de.gedoplan.showcase.greeting.extension.deployment;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.hamcrest.Matchers.containsString;

public class GreetingExtensionTest {

  @RegisterExtension
  static final QuarkusUnitTest config = new QuarkusUnitTest()
    .withEmptyApplication(); 

  @Test
  public void testGreeting() {
    RestAssured.when().get("/greeting").then().statusCode(200).body(containsString("Hello")); 
  }

}