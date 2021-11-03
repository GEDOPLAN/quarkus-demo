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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;

@ApplicationScoped
@Path(PersonResource.PATH)
public class PersonResource {
  public static final String PATH = "person";
  public static final String ID_NAME = "id";
  public static final String ID_TEMPLATE = "{" + ID_NAME + "}";

  @Inject
  PersonRepository personRepository;

  @Inject
  Log log;

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Operation(summary = "Get all persons")
  public List<Person> getAll() {
    return this.personRepository.findAll();
  }

  @GET
  @Path(ID_TEMPLATE)
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Operation(summary = "Get a person")
  @APIResponse(description = "Found person (JSON/XML)")
  @APIResponse(responseCode = "404", description = "Person not found")
  public Person getById(@PathParam(ID_NAME) Integer id) {
    Person person = this.personRepository.findById(id);
    if (person != null) {
      return person;
    }

    throw new NotFoundException();
  }

  @PUT
  @Path(ID_TEMPLATE)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Operation(summary = "Update a person")
  @APIResponse(responseCode = "400", description = "Id of person must not be changed")
  @APIResponse(responseCode = "404", description = "Person not found")
  @Transactional(rollbackOn = Exception.class)
  public void update(@PathParam(ID_NAME) Integer id, Person Person) {
    if (!id.equals(Person.getId())) {
      throw new BadRequestException("id of updated object must not be changed");
    }

    Person person = this.personRepository.findById(id);
    if (person == null) {
      throw new NotFoundException();
    }

    this.personRepository.merge(Person);
  }

  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Operation(summary = "Insert a new person")
  @APIResponse(responseCode = "400", description = "Id of person must not be pre-set")
  public Response create(Person Person, @Context UriInfo uriInfo) {
    if (Person.getId() != null) {
      throw new BadRequestException("id of new entry must not be pre-set");
    }

    this.personRepository.persist(Person);

    URI createdUri = uriInfo
        .getAbsolutePathBuilder()
        .path(ID_TEMPLATE)
        .resolveTemplate(ID_NAME, Person.getId())
        .build();
    return Response.created(createdUri).build();
  }

  @DELETE
  @Path(ID_TEMPLATE)
  @Operation(summary = "Delete a person")
  public void delete(@PathParam(ID_NAME) Integer id) {
    this.personRepository.removeById(id);
  }

}
