package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.restcountries.eu.CountryApi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("country")
@ApplicationScoped
public class CountryResource {
  @Inject
  @RestClient
  CountryApi countryClient;

  @GET
  @Path("count")
  @Produces(MediaType.APPLICATION_JSON)
  public int getCount() {
    return this.countryClient.getAll().size();
  }
}
