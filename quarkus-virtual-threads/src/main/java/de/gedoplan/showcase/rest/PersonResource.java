package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Person;
import de.gedoplan.showcase.persistence.PersonReactiveRepository;
import de.gedoplan.showcase.persistence.PersonRepository;
import de.gedoplan.showcase.service.PersonService;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("persons")
public class PersonResource {

  @Inject
  PersonRepository personRepository;

  @Inject
  PersonReactiveRepository personReactiveRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> get() {
    return this.personRepository.findAll();
  }

  @GET
  @Path("reactive")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<List<Person>> getReactive() {
    return this.personReactiveRepository.listAll();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response post(Person person, @Context UriInfo uriInfo) {
    if (person.getId() != null) {
      throw new BadRequestException("Id must not be set");
    }

    this.personRepository.persist(person);
    this.personRepository.flush();

    URI uri = uriInfo
      .getAbsolutePathBuilder()
      .path(person.getId().toString())
      .build();
    return Response
      .created(uri)
      .build();
  }

  @Inject
  PersonService personService;

  @Path("avgAge")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public double getAverageAge() {
    return this.personService.getAverageAge();
  }
}
