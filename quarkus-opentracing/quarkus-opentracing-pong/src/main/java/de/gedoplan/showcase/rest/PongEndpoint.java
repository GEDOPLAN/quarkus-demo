package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("pong")
@ApplicationScoped
public class PongEndpoint {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String get() {
    return "Pong!";
  }
}
