package de.gedoplan.showcase;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class MetricsTest {
    
    @Test
    public void testCustom() {
        given()
          .when()
            .accept(MediaType.APPLICATION_JSON)
            .get("/q/metrics/application")
          .then()
             .statusCode(200)
             .body("answerToLifeUniverseAndEverything", is(42));
    }

}
