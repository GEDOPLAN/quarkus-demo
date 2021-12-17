package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.service.GreetingService;
import jdk.jfr.Percentage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("greeting")
public class GreetingResource {

  @Inject GreetingService greetingService;

  @GET
  @Produces("text/plain")
  public String getGreeting() {
    return greetingService.getGreeting();
  }
}
