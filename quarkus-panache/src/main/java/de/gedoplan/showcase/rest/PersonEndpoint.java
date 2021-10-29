package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Person;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.net.URI;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@ApplicationScoped
@Path(PersonEndpoint.PATH)
public class PersonEndpoint {
  public static final String PATH = "persons";
  public static final String ID_NAME = "id";
  public static final String ID_TEMPLATE = "{" + ID_NAME + "}";

  @Inject
  Log log;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get all persons")
  public List<Person> getAll(@QueryParam("name") String name) {
    if (name ==null)
      return Person.listAll();
    else
      return Person.list("name", name);
  }

  @GET
  @Path(ID_TEMPLATE)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get a person")
  @APIResponse(description = "Found person (JSON/XML)")
  @APIResponse(responseCode = "404", description = "Person not found")
  public Person getById(@PathParam(ID_NAME) Long id) {
    Person person = Person.findById(id);
    if (person != null) {
      return person;
    }

    throw new NotFoundException();
  }

  @PUT
  @Path(ID_TEMPLATE)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update a person")
  @APIResponse(responseCode = "400", description = "Id of person must not be changed")
  @APIResponse(responseCode = "404", description = "Person not found")
  @Transactional(rollbackOn = Exception.class)
  public void update(@PathParam(ID_NAME) Long id, Person newPerson) {
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
  @Operation(summary = "Insert a new person")
  @APIResponse(responseCode = "400", description = "Id of person must not be pre-set")
  @Transactional(rollbackOn = Exception.class)
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
  @Path(ID_TEMPLATE)
  @Operation(summary = "Delete a person")
  @Transactional(rollbackOn = Exception.class)
  public void delete(@PathParam(ID_NAME) Long id) {
    Person.deleteById(id);
  }

}
