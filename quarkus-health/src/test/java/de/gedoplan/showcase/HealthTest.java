package de.gedoplan.showcase;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HealthTest {
    
    @Test
    public void testLiveness() {
        given()
          .when()
            .get("/q/health/live")
          .then()
             .statusCode(200)
             .body("status", is("UP"));
    }

    @Test
    public void testReadiness() {
        given()
          .when()
            .get("/q/health/ready")
          .then()
             .statusCode(503)
             .body("status", is("DOWN"));
    }

}
