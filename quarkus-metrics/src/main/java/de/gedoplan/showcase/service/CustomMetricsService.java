package de.gedoplan.showcase.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class CustomMetricsService {

  @Gauge(name = "answerToLifeUniverseAndEverything", absolute = true, unit = MetricUnits.NONE)
  public long getAnswerToLifeUniverseAndEverything() {
    return 42;
  }

  /*
   * Metrics collection via @Gauge (and @Timed, @Counted, ...) is done by CDI interceptors and happens therefore only,
   * if bean instances have been created. This demo 'service' isn't used anywhere else, leaving it uninstantiated.
   * Therefore we cheat a little bit by observing the application scope initialization event, forcing bean instantiation then.
   */
  void startUp(@Observes StartupEvent event) {
  }

}
