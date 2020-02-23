package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.service.PongService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("ping")
@ApplicationScoped
public class PingEndpoint {

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
