package de.gedoplan.showcase.mock;

import de.gedoplan.showcase.service.GreetingService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
public class FrenchGreetingService extends GreetingService {
  @Override public String getGreeting() {
    return "Salut monde!";
  }
}
