package de.gedoplan.showcase.service;

import org.jboss.logging.Logger;

import de.gedoplan.showcase.model.Hotel;
import de.gedoplan.showcase.persistence.HotelRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DemoDataService {

  @Inject
  HotelRepository hotelRepository;

  @Inject
  Logger logger;

  @Transactional
  void initDemoData(@Observes StartupEvent startupEvent) {
    if (this.hotelRepository.countAll() == 0) {
      this.hotelRepository.persist(new Hotel('1'));
      this.hotelRepository.persist(new Hotel('2'));
      this.hotelRepository.persist(new Hotel('3'));
    }
  }

  public void showBookingSummary() {
    logger.debug("Hotel booking summary:");

    this.hotelRepository
    .findAll()
    .forEach(x -> logger.debugf("  Hotel %s: %s (%s)", x.getId(), x.getBookingType(), x.getLraId()));
  }
  
}
