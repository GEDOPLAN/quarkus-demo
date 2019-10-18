package de.gedoplan.showcase.restcountries.eu;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
