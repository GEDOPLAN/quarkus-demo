package de.gedoplan.health;

import de.gedoplan.service.ReadinessSimulationService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
@Readiness
public class ReadinessCheck implements HealthCheck {

  @Inject
  ReadinessSimulationService readinessSimulationService;

  @Override
  public HealthCheckResponse call() {
    return HealthCheckResponse
        .named("readinessSimulation")
        .status(this.readinessSimulationService.isReady())
        .withData("memory", Runtime.getRuntime().freeMemory())
        .build();
  }

}
