package de.gedoplan.showcase.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
