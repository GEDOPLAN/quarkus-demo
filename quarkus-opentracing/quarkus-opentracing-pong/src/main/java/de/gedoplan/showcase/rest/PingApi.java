package de.gedoplan.showcase.rest;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("ping")
@RegisterRestClient
public interface PingApi {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Fallback(fallbackMethod = "defaultPing")
  public String get(@QueryParam("prefix") @DefaultValue("") String prefix);

  default String defaultPing(String prefix) {
    return prefix + "Ping?";
  }
}
