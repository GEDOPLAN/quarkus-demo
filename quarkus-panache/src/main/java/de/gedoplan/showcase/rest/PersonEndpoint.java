package de.gedoplan.showcase.rest;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;

import de.gedoplan.showcase.entity.Person;

@ApplicationScoped
@Path("v1/persons")
public class PersonEndpoint {

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
