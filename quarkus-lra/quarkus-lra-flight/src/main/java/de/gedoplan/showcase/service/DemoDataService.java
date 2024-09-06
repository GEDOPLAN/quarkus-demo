package de.gedoplan.showcase.service;

import org.jboss.logging.Logger;

import de.gedoplan.showcase.model.Flight;
import de.gedoplan.showcase.persistence.FlightRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DemoDataService {

  @Inject
  FlightRepository flightRepository;

  @Inject
  Logger logger;

  @Transactional
  void initDemoData(@Observes StartupEvent startupEvent) {
    if (this.flightRepository.countAll() == 0) {
      this.flightRepository.persist(new Flight('a'));
      this.flightRepository.persist(new Flight('b'));
      this.flightRepository.persist(new Flight('c'));
    }
  }

  public void showBookingSummary() {
    logger.debug("Flight booking summary:");

    this.flightRepository
    .findAll()
    .forEach(x -> logger.debugf("  Flight %s: %s (%s)", x.getId(), x.getBookingType(), x.getLraId()));
  }
  
}
