package de.gedoplan.showcase.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("pong")
@ApplicationScoped
public class PongResource {

  @Inject
  @RestClient
  PingApi pingApi;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@QueryParam("prefix") @DefaultValue("") String prefix) {
    String text = prefix + "Pong!";
    return text.length() < 20 ? this.pingApi.get(text) : text;
  }

}
