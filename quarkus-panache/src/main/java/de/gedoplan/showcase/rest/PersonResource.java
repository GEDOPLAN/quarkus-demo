package de.gedoplan.showcase.rest;

import java.net.URI;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;

import de.gedoplan.showcase.entity.Person;

@ApplicationScoped
@Path("v1/persons")
public class PersonResource {

  @Inject
  Log log;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> getAll(@QueryParam("name") String name) {
    if (name ==null)
      return Person.listAll();
    else
      return Person.list("name", name);
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getById(@PathParam("id") Long id) {
    Person person = Person.findById(id);
    if (person != null) {
      return person;
    }

    throw new NotFoundException();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public void update(@PathParam("id") Long id, Person newPerson) {
    if (newPerson.id != null && !id.equals(newPerson.id)) {
      throw new BadRequestException("id of updated object must not be changed");
    }

    Person person = Person.findById(id);
    if (person == null) {
      throw new NotFoundException();
    }

    person.setValues(newPerson);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response create(Person person, @Context UriInfo uriInfo) {
    if (person.id != null) {
      throw new BadRequestException("id of new entry must not be pre-set");
    }

    person.persist();

    URI createdUri = uriInfo
        .getAbsolutePathBuilder()
        .path(person.id.toString())
        .build();
    return Response.created(createdUri).build();
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public void delete(@PathParam("id") Long id) {
    Person.deleteById(id);
  }

}
