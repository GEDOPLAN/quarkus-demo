package de.gedoplan.showcase.mock;

import de.gedoplan.showcase.service.GreetingService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
public class SpanishGreetingService extends GreetingService {
  @Override public String getGreeting() {
    return "¡Hola mundo!";
  }
}
