package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.PushService1;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/push")
public class PushResource {

  @Inject
  PushService1 pushService1;

  @PUT
  @Consumes("*/*")
  public void push(String message) {
    this.pushService1.send(message + " (from REST API)");
  }
}