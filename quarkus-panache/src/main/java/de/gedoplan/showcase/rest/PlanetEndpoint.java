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

import de.gedoplan.showcase.entity.Planet;
import de.gedoplan.showcase.persistence.PlanetRepository;

@ApplicationScoped
@Path("v1/planets")
public class PlanetEndpoint {

  @Inject
  PlanetRepository planetRepository;

  @Inject
  Log log;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Planet> getAll() {
    return this.planetRepository.listAll();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Planet getById(@PathParam("id") Long id) {
    Planet planet = planetRepository.findById(id);
    if (planet != null) {
      return planet;
    }

    throw new NotFoundException();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public void update(@PathParam("id") Long id, Planet newPlanet) {
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
  @Transactional
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
  @Path("{id}")
  @Transactional
  public void delete(@PathParam("id") Long id) {
    this.planetRepository.deleteById(id);
  }

}
