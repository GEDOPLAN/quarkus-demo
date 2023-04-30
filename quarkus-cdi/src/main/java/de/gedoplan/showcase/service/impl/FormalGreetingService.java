package de.gedoplan.showcase.service.impl;

import de.gedoplan.showcase.service.Formal;
import de.gedoplan.showcase.service.Greeting;
import de.gedoplan.showcase.service.GreetingService;
import de.gedoplan.showcase.service.GreetingType;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Formal
@Greeting(type = GreetingType.FORMAL)
public class FormalGreetingService implements GreetingService {

  @Override
  public String getGreeting() {
    return "Dear Madams and Sirs";
  }

}
