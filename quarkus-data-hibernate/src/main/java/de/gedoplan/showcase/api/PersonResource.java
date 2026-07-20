package de.gedoplan.showcase.api;

import de.gedoplan.showcase.entity.Person;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/person")
public class PersonResource {
  @Inject
  Person.Repository personRepository;

  @GET
  public List<Person> getAll() {
    return personRepository.listAll();
  }
}
