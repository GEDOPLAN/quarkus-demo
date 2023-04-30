package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.service.GreetingService;
import jdk.jfr.Percentage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

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
