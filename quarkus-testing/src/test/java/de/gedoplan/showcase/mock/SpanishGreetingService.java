package de.gedoplan.showcase.mock;

import de.gedoplan.showcase.service.GreetingService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
public class SpanishGreetingService extends GreetingService {
  @Override public String getGreeting() {
    return "¡Hola mundo!";
  }
}
