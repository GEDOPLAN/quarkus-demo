package de.gedoplan.showcase.profile;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class TestProfileA implements QuarkusTestProfile {

  @Override public Map<String, String> getConfigOverrides() {
    return Map.of(
      "greeting.message", "Hallo",
      "greeting.name", "Welt");
  }
}
