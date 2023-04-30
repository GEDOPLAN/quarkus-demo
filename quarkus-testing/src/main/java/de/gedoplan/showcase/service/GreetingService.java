package de.gedoplan.showcase.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class GreetingService {

  @Inject
  @ConfigProperty(name = "greeting.message")
  String message;

  @Inject
  @ConfigProperty(name = "greeting.suffix", defaultValue = "!")
  String suffix;

  @Inject
  @ConfigProperty(name = "greeting.name")
  Optional<String> name;

  public String getGreeting() {
    return this.message + " " + this.name.orElse("world") + this.suffix;
  }

}
