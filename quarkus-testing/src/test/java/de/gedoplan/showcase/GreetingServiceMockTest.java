package de.gedoplan.showcase;

import de.gedoplan.showcase.service.GreetingService;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GreetingServiceMockTest {

  @Inject
  GreetingService greetingService;

  @BeforeAll
  static void beforeAll() {
    GreetingService mock = Mockito.mock(GreetingService.class);
    Mockito.when(mock.getGreeting()).thenReturn("Guten Tag!");
    QuarkusMock.installMockForType(mock, GreetingService.class);
  }

  @Test
  public void testGetGreeting() {
    String greeting = greetingService.getGreeting();
    assertEquals("Guten Tag!", greeting);
  }
}
