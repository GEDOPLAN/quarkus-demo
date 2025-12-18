package de.gedoplan.showcase.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class PersonRepositoryTest {

  @Inject
  PersonRepository personRepository;

  @Test
  public void testDagobertAndDonalDuckExist() {
    List<Person> persons = this.personRepository.findAll().toList();

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

}
