package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.PushService1;
import de.gedoplan.showcase.service.PushService2;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/push")
public class PushResource {

  @Inject
  PushService1 pushService1;

  @Inject
  PushService2 pushService2;

  @PUT
  @Consumes("*/*")
  public void push(String message) {
    this.pushService1.send(message + " (from REST API)");
    this.pushService2.send(message + " (from REST API)");
  }
}