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
import org.jboss.logging.Logger;

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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> getAll() {
    return this.personRepository.findAll().toList();
  }

  @GET
  @Path(ID_TEMPLATE)
  @Produces(MediaType.APPLICATION_JSON)
  public Person getById(@PathParam(ID_NAME) Integer id) {
    return this.personRepository
      .findById(id)
      .orElseThrow(NotFoundException::new);
  }

  @PUT
  @Path(ID_TEMPLATE)
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional(rollbackOn = Exception.class)
  public void update(@PathParam(ID_NAME) Integer id, Person person) {
    if (!id.equals(person.getId())) {
      throw new BadRequestException("id of updated object must not be changed");
    }

    // Assure person exists
    getById(id);

    this.personRepository.update(person);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional(rollbackOn = Exception.class)
  public Response create(Person person, @Context UriInfo uriInfo) {
    if (person.getId() != null) {
      throw new BadRequestException("id of new entry must not be pre-set");
    }

    this.personRepository.insert(person);

    URI createdUri = uriInfo
        .getAbsolutePathBuilder()
        .path(ID_TEMPLATE)
        .resolveTemplate(ID_NAME, person.getId())
        .build();
    return Response.created(createdUri).build();
  }

  @DELETE
  @Path(ID_TEMPLATE)
  @Transactional(rollbackOn = Exception.class)
  public void delete(@PathParam(ID_NAME) Integer id) {
    this.personRepository.deleteById(id);
  }

}
