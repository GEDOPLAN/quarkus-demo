package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.service.PongService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("ping")
@ApplicationScoped
public class PingResource {

  @Inject
  PongService pingService;

  @Inject
  @RestClient
  PongApi pongApi;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@QueryParam("prefix") @DefaultValue("") String prefix) {
    return this.pongApi.get(prefix + this.pingService.getPing());
  }
}
