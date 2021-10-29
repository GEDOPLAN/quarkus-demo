package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Planet;
import de.gedoplan.showcase.entity.Planet;
import de.gedoplan.showcase.persistence.PlanetRepository;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@ApplicationScoped
@Path(PlanetEndpoint.PATH)
public class PlanetEndpoint {
  public static final String PATH = "planets";
  public static final String ID_NAME = "id";
  public static final String ID_TEMPLATE = "{" + ID_NAME + "}";

  @Inject
  PlanetRepository planetRepository;

  @Inject
  Log log;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get all planets")
  public List<Planet> getAll() {
    return this.planetRepository.listAll();
  }

  @GET
  @Path(ID_TEMPLATE)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get a planet")
  @APIResponse(description = "Found planet (JSON)")
  @APIResponse(responseCode = "404", description = "Planet not found")
  public Planet getById(@PathParam(ID_NAME) Long id) {
    Planet planet = planetRepository.findById(id);
    if (planet != null) {
      return planet;
    }

    throw new NotFoundException();
  }

  @PUT
  @Path(ID_TEMPLATE)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update a planet")
  @APIResponse(responseCode = "400", description = "Id of planet must not be changed")
  @APIResponse(responseCode = "404", description = "Planet not found")
  @Transactional(rollbackOn = Exception.class)
  public void update(@PathParam(ID_NAME) Long id, Planet newPlanet) {
    if (newPlanet.getId() != null && !id.equals(newPlanet.getId())) {
      throw new BadRequestException("id of updated object must not be changed");
    }

    Planet planet = this.planetRepository.findById(id);
    if (planet == null) {
      throw new NotFoundException();
    }

    planet.setValues(newPlanet);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Insert a new planet")
  @APIResponse(responseCode = "400", description = "Id of planet must not be pre-set")
  @Transactional(rollbackOn = Exception.class)
  public Response create(Planet planet, @Context UriInfo uriInfo) {
    if (planet.getId() != null) {
      throw new BadRequestException("id of new entry must not be pre-set");
    }

    planetRepository.persist(planet);

    URI createdUri = uriInfo
        .getAbsolutePathBuilder()
        .path(planet.getId().toString())
        .build();
    return Response.created(createdUri).build();
  }

  @DELETE
  @Path(ID_TEMPLATE)
  @Operation(summary = "Delete a planet")
  @Transactional(rollbackOn = Exception.class)
  public void delete(@PathParam(ID_NAME) Long id) {
    this.planetRepository.deleteById(id);
  }

}
