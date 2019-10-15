package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Person;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(PersonResource.PATH)
public interface PersonResource {
  public static final String PATH = "person";
  public static final String ID_NAME = "id";
  public static final String ID_TEMPLATE = "{" + ID_NAME + "}";

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Person> getAll();

  @GET
  @Path(ID_TEMPLATE)
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Person getById(@PathParam(ID_NAME) Integer id);

  @PUT
  @Path(ID_TEMPLATE)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public void updatePerson(@PathParam(ID_NAME) Integer id, Person Person);

  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Response createPerson(Person Person, @Context UriInfo uriInfo) throws URISyntaxException;

  @DELETE
  @Path(ID_TEMPLATE)
  public void deletePerson(@PathParam(ID_NAME) Integer id);
}
