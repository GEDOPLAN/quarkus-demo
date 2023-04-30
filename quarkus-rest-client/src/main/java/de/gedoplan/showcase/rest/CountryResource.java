package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.restcountries.eu.CountryApi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
