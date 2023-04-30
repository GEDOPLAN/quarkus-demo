package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;

import java.net.URI;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@ApplicationScoped
@Path(PersonResource.PATH)
public class PersonResource {
  public static final String PATH = "person";
  public static final String ID_NAME = "id";
  public static final String ID_TEMPLATE = "{" + ID_NAME + "}";

  @Inject
  PersonRepository personRepository;

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Timed(name = "personList", absolute = true)
  public List<Person> getAll() {
    return this.personRepository.findAll();
  }

  @GET
  @Path(ID_TEMPLATE)
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Person getById(@PathParam(ID_NAME) Integer id) {
    return this.personRepository
        .findById(id)
        .orElseThrow(NotFoundException::new);
  }

  @PUT
  @Path(ID_TEMPLATE)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public void updatePerson(@PathParam(ID_NAME) Integer id, Person Person) {
    if (!id.equals(Person.getId())) {
      throw new BadRequestException("id of updated object must be unchanged");
    }

    this.personRepository.merge(Person);
  }

  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Counted(unit = MetricUnits.NONE, name = "createPerson", absolute = true)
  public Response createPerson(Person Person, @Context UriInfo uriInfo) {
    if (Person.getId() != null) {
      throw new BadRequestException("id of new entry must not be set");
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
  public void deletePerson(@PathParam(ID_NAME) Integer id) {
    this.personRepository.removeById(id);
  }

}
