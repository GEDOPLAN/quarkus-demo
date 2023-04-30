package de.gedoplan.showcase.restcountries.eu;

import java.util.Collection;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("v2")
@RegisterRestClient
public interface CountryApi {

  @GET
  @Path("all")
  @Produces("application/json")
  Collection<Country> getAll();

  @GET
  @Path("alpha/{code}")
  @Produces("application/json")
  Country getByCode(@PathParam("code") String code);

}
