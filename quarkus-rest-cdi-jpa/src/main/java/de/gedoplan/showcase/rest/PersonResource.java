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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

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
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get all persons")
  public List<Person> getAll() {
    return this.personRepository.findAll();
  }

  @GET
  @Path(ID_TEMPLATE)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get a person")
  @APIResponse(description = "Found person (JSON/XML)")
  @APIResponse(responseCode = "404", description = "Person not found")
  public Person getById(@PathParam(ID_NAME) Integer id) {
    return this.personRepository
      .findById(id)
      .orElseThrow(NotFoundException::new);
  }

  @PUT
  @Path(ID_TEMPLATE)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update a person")
  @APIResponse(responseCode = "400", description = "Id of person must not be changed")
  @APIResponse(responseCode = "404", description = "Person not found")
  @Transactional(rollbackOn = Exception.class)
  public void update(@PathParam(ID_NAME) Integer id, Person person) {
    if (!id.equals(person.getId())) {
      throw new BadRequestException("id of updated object must not be changed");
    }

    // Assure person exists
    getById(id);

    this.personRepository.merge(person);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Insert a new person")
  @APIResponse(responseCode = "400", description = "Id of person must not be pre-set")
  public Response create(Person person, @Context UriInfo uriInfo) {
    if (person.getId() != null) {
      throw new BadRequestException("id of new entry must not be pre-set");
    }

    this.personRepository.persist(person);

    URI createdUri = uriInfo
        .getAbsolutePathBuilder()
        .path(ID_TEMPLATE)
        .resolveTemplate(ID_NAME, person.getId())
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
