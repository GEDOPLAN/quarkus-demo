package de.gedoplan.showcase.rest;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("pong")
@RegisterRestClient
public interface PongApi {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Fallback(fallbackMethod = "defaultPong")
  public String get(@QueryParam("prefix") @DefaultValue("") String prefix);

  default String defaultPong(String prefix) {
    return prefix + "Pong?";
  }
}
