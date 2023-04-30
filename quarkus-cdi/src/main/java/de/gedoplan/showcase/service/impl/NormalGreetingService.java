package de.gedoplan.showcase.service.impl;

import de.gedoplan.showcase.service.Greeting;
import de.gedoplan.showcase.service.GreetingService;
import de.gedoplan.showcase.service.GreetingType;

import java.time.LocalTime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

@ApplicationScoped
@Default
@Greeting(type = GreetingType.NORMAL)
public class NormalGreetingService implements GreetingService {

  @Override
  public String getGreeting() {
    int hourOfDay = LocalTime.now().getHour();
    if (hourOfDay < 10) {
      return "Good morning";
    } else if (hourOfDay < 18) {
      return "Good afternoon";
    } else {
      return "Good evening";
    }
  }
}
