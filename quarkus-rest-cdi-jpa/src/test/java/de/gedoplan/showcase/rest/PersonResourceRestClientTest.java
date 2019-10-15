package de.gedoplan.showcase.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import de.gedoplan.showcase.entity.Person;

import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PersonResourceRestClientTest {

  @TestHTTPResource
  URL baseUrl;

  PersonResource personResource;

  @BeforeEach
  void before() {
    this.personResource = RestClientBuilder.newBuilder()
        .baseUrl(this.baseUrl)
        .build(PersonResource.class);
  }

  @Test
  void test_01_DagobertAndDonalDuckExist() {
    List<Person> persons = this.personResource.getAll();

    boolean foundDagobert = false;
    boolean foundDonald = false;

    for (Person person : persons) {
      if ("Duck".equals(person.getName())) {
        if ("Dagobert".equals(person.getFirstname())) {
          foundDagobert = true;
        }
        if ("Donald".equals(person.getFirstname())) {
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

    // Post new Person
    Response response = this.personResource.createPerson(person);
    assertEquals(Status.CREATED.getStatusCode(), response.getStatus(), "Wrong status code");

    String newPersonUrl = response.getHeaderString(HttpHeaders.LOCATION);
    assertNotNull(newPersonUrl, "New person URL must not be null");

    int newPersonId = Integer.parseInt(newPersonUrl.substring(newPersonUrl.lastIndexOf('/') + 1));

    // Get new person and compare
    Person newPerson = this.personResource.getById(newPersonId);
    assertEquals(person.getName(), newPerson.getName(), "Wrong last name");
    assertEquals(person.getFirstname(), newPerson.getFirstname(), "Wrong first name");

    // Delete new person
    this.personResource.deletePerson(newPersonId);

    // Get new person again; expect NOT_FOUND
    try {
      this.personResource.getById(newPersonId);

      fail("Entry should have been deleted before");
    } catch (WebApplicationException e) {
      assertEquals(Status.NOT_FOUND.getStatusCode(), e.getResponse().getStatus(), "Wrong status code");
    } catch (Exception e) {
      fail("Wrong exception");
    }
  }

}
