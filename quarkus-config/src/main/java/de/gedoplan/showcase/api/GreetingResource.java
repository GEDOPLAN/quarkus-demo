package de.gedoplan.showcase.api;

import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

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