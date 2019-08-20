package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("reload")
public class DummyResource {

  @GET
  public void reload() {
  }
}
