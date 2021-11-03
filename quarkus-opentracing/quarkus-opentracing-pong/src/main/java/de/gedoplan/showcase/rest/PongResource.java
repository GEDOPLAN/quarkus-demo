package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("pong")
@ApplicationScoped
public class PongResource {

  @Inject
  @RestClient
  PingApi pingApi;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@QueryParam("prefix") @DefaultValue("") String prefix) {
    String text = prefix + "Pong!";
    return text.length() < 20 ? this.pingApi.get(text) : text;
  }

}
