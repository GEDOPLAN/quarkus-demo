package de.gedoplan.showcase.command;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusMainTest
public class PicocliMainTest {

  @Test
  @Launch(value = {})
  public void testLaunchWithoutArgs(LaunchResult launchResult) {
    assertEquals(0, launchResult.exitCode());
  }

  @Test
  @Launch(value = {"--from=GBP", "--to=CHF", "--amount=1"})
  public void testLaunchGBP2CHF(LaunchResult launchResult) {
    assertEquals(0, launchResult.exitCode());
    assertEquals("1.0000 GBP = 1.2090 CHF", launchResult.getOutput());
  }
}
