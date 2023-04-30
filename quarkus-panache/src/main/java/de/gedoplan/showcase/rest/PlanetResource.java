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

import de.gedoplan.showcase.entity.Planet;
import de.gedoplan.showcase.persistence.PlanetRepository;

@ApplicationScoped
@Path("v1/planets")
public class PlanetResource {

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
