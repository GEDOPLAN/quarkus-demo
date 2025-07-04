package de.gedoplan.showcase.service;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PingService {
  @WithSpan
  public String getPing() {
    return "Ping!";
  }
}
