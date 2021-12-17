package de.gedoplan.showcase;

import de.gedoplan.showcase.profile.TestProfileA;
import de.gedoplan.showcase.service.GreetingService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestProfile(TestProfileA.class)
public class GreetingServiceProfileATest {

  @Inject GreetingService greetingService;

  @Test
  public void testGetGreeting() {
    String greeting = greetingService.getGreeting();
    assertEquals("Hallo Welt!", greeting);
  }
}
