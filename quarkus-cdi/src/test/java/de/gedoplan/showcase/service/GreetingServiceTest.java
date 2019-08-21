package de.gedoplan.showcase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingServiceTest {

  @Inject
  @Formal
  GreetingService greetingService;

  @Test
  public void testGreetingService() {
    String greeting = this.greetingService.getGreeting();

    assertEquals("Dear Madams and Sirs", greeting);
  }
}
