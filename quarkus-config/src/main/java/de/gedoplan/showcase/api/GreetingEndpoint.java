package de.gedoplan.showcase.api;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingEndpoint {

  @Inject
  @ConfigProperty(name = "greeting.message")
  String message;

  @Inject
  @ConfigProperty(name = "greeting.suffix", defaultValue = "!")
  String suffix;

  @Inject
  @ConfigProperty(name = "greeting.name")
  Optional<String> name;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return this.message + " " + this.name.orElse("world") + this.suffix;
  }
}