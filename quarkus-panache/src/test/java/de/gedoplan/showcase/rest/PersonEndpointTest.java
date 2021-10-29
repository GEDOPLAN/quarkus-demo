package de.gedoplan.showcase.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.gedoplan.showcase.entity.Person;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;

@QuarkusTest
public class PersonEndpointTest {

  @Test
  public void test_01_DagobertAndDonalDuckExist() {
    List<Person> persons = given()
        .when()
        .get("/persons")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(new TypeRef<List<Person>>() {});

    boolean foundDagobert = false;
    boolean foundDonald = false;

    for (Person person : persons) {
      if ("Duck".equals(person.name)) {
        if ("Dagobert".equals(person.firstname)) {
          foundDagobert = true;
        }
        if ("Donald".equals(person.firstname)) {
          foundDonald = true;
        }
      }
    }

    assertTrue(foundDagobert, "Dagobert not found");
    assertTrue(foundDonald, "Donald not found");
  }

  @Test
  public void test_02_PostGetDelete() throws Exception {
    Person person = new Person("Duck", "Tick-" + UUID.randomUUID().toString());

    String newPersonUrl = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(person)
        .post("/persons")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .header(HttpHeaders.LOCATION);

    assertNotNull(newPersonUrl, "New person URL must not be null");

    given()
        .when()
        .get(newPersonUrl)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("name", is(person.name))
        .body("firstname", is(person.firstname));

    given()
        .when()
        .delete(newPersonUrl)
        .then()
        .statusCode(HttpStatus.SC_NO_CONTENT);

    given()
        .when()
        .get(newPersonUrl)
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

}
