package de.gedoplan.showcase.service.mock;

import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SlowDownService {

  @Inject
  @ConfigProperty(name = "slowdown")
  boolean slowdown;

  @SneakyThrows
  public void delay(long millis) {
    if (this.slowdown) {
      Thread.sleep(millis);
    }
  }
}
