package de.gedoplan.showcase.profile;

import de.gedoplan.showcase.mock.FrenchGreetingService;
import de.gedoplan.showcase.mock.SpanishGreetingService;
import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Set;

public class TestProfileD implements QuarkusTestProfile {

  @Override public Set<Class<?>> getEnabledAlternatives() {
    return Set.of(FrenchGreetingService.class);
  }
}
