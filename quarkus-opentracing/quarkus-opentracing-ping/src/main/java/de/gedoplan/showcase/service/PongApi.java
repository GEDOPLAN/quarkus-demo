package de.gedoplan.showcase.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("pong")
@RegisterRestClient
public interface PongApi {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Fallback(fallbackMethod = "defaultPong")
  public String getPong();

  default String defaultPong() {
    return "???";
  }
}
