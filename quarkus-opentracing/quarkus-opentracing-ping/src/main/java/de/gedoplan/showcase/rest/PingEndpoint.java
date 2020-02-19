package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.service.PongService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ping")
@ApplicationScoped
public class PingEndpoint {

  @Inject
  PongService pongService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String get() {
    return "Ping! " + this.pongService.getReply();
  }
}
