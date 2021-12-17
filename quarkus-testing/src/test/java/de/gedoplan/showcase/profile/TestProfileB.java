package de.gedoplan.showcase.profile;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class TestProfileB implements QuarkusTestProfile {

  @Override public String getConfigProfile() {
    return "testB";
  }
}
