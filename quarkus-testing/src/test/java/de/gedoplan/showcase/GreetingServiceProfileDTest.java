package de.gedoplan.showcase;

import de.gedoplan.showcase.profile.TestProfileC;
import de.gedoplan.showcase.profile.TestProfileD;
import de.gedoplan.showcase.service.GreetingService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestProfile(TestProfileD.class)
public class GreetingServiceProfileDTest {

  @Inject GreetingService greetingService;

  @Test
  public void testGetGreeting() {
    String greeting = greetingService.getGreeting();
    assertEquals("Salut monde!", greeting);
  }
}
