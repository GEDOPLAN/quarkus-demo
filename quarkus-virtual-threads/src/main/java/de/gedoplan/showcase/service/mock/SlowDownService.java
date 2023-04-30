package de.gedoplan.showcase.service.mock;

import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SlowDownService {

  @Inject
  @ConfigProperty(name = "slowdown", defaultValue = "false")
  boolean slowdown;

  @SneakyThrows
  public void delay(long millis) {
    if (this.slowdown) {
      Thread.sleep(millis);
    }
  }
}
