package de.gedoplan.showcase.profile;

import de.gedoplan.showcase.mock.SpanishGreetingService;
import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Set;

public class TestProfileC implements QuarkusTestProfile {

  @Override public Set<Class<?>> getEnabledAlternatives() {
    return Set.of(SpanishGreetingService.class);
  }
}
