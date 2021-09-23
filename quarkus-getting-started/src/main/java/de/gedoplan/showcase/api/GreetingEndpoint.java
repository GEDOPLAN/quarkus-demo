package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.GreetingService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingEndpoint {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Hello";
  }

  @Inject
  GreetingService service;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/greeting/{name}")
  public String greeting(@PathParam("name") String name) {
    return this.service.greeting(name);
  }
}