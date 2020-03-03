package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/")
public class DummyEndpoint {

  // Dummy GET method forcing hot reload after code changes
  @GET
  public void get() {
  }
}
