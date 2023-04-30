package de.gedoplan.showcase;

import de.gedoplan.showcase.service.GreetingService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GreetingServiceTest {

  @Inject GreetingService greetingService;

  @Test
  public void testGetGreeting() {
    String greeting = greetingService.getGreeting();
    assertEquals("Hello world!", greeting);
  }
}
