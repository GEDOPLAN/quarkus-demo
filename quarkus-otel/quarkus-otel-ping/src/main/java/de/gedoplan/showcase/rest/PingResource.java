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
import org.jboss.logging.Logger;

import de.gedoplan.showcase.service.PingService;

@Path("ping")
@ApplicationScoped
public class PingResource {

  @Inject
  PingService pingService;

  @Inject
  @RestClient
  PongApi pongApi;

  @Inject
  Logger logger;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@QueryParam("prefix") @DefaultValue("") String prefix) {
    this.logger.debugf("get(%s)", prefix);

    return this.pongApi.get(prefix + this.pingService.getPing());
  }
}
