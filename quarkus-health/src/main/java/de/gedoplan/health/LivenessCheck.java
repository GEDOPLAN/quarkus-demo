package de.gedoplan.health;

import de.gedoplan.service.LivenessSimulationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
@Liveness
public class LivenessCheck implements HealthCheck {

  @Inject
  LivenessSimulationService livenessSimulationService;

  @Override
  public HealthCheckResponse call() {
    return HealthCheckResponse
        .named("livenessSimulation")
        .state(this.livenessSimulationService.isLive())
        .build();
  }

}
