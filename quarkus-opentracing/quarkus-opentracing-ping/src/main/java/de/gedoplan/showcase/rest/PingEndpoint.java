package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.service.PongApi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("ping")
@ApplicationScoped
public class PingEndpoint {

  @Inject
  @RestClient
  PongApi pongApi;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String get() {
    String pong = this.pongApi.getPong();
    return "Ping! " + pong;
  }
}
