package de.gedoplan.showcase;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.service.PersonReceiver;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonSendTest {
  @Inject
  PersonReceiver personReceiver;

  @Inject
  Log log;

  @Test
  void testSendPerson() throws Exception {
    Person sent = new Person("Duck", "Dagobert");
    String sentJson = JsonbBuilder.create().toJson(sent);

    given()
      .header("Content-type", "application/json")
      .and()
      .body(sentJson)
      .when()
      .post("/persons")
      .then()
      .statusCode(201);

    Person received = personReceiver.pollReceived(200);
    log.debug("Received: " + received);
    Assertions.assertEquals(sent, received);

  }
}
