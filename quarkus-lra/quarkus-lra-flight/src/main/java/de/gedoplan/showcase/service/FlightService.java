package de.gedoplan.showcase.service;

import org.jboss.logging.Logger;

import de.gedoplan.showcase.model.BookingType;
import de.gedoplan.showcase.persistence.FlightRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FlightService {

  @Inject
  FlightRepository flightRepository;

  @Inject
  DemoDataService demoDataService;

  @Inject
  Logger logger;

  public void bookFlight(char whatToBook, String lraId) {
    logger.debugf("bookFlight(%c, %s)", whatToBook, lraId);

    try {
      var flight = this.flightRepository
          .findById(whatToBook)
          .orElseThrow(() -> new NotFoundException("Flight " + whatToBook + " not found"));

      if (flight.getBookingType() != BookingType.FREE)
        throw new BadRequestException("Flight " + whatToBook + " is not available anymore");

      flight.setBookingType(BookingType.BOOKED);
      flight.setLraId(lraId);

    } catch (Exception e) {
      logger.error(e);
      throw e;

    } finally {
      this.demoDataService.showBookingSummary();
    }
  }

  public void unbookFlights(String lraId) {
    logger.debugf("unbookFlights(%s)", lraId);

    try {
      this.flightRepository
          .findByLraId(lraId)
          .forEach(flight -> {
            flight.setLraId(null);
            flight.setBookingType(BookingType.FREE);
      });
    } catch (Exception e) {
      logger.error(e);
      throw e;

    } finally {
      this.demoDataService.showBookingSummary();
    }
  }

  // public void unbookFlight(char whatToUnbook, String lraId) {
  // logger.debugf("unbookFlight(%c, %s)", whatToUnbook, lraId);

  // try {
  // if (lraId != null) {
  // this.flightRepository
  // .findById(whatToUnbook)
  // .ifPresent(flight -> {
  // if (lraId.equals(flight.getLraId())) {
  // flight.setLraId(null);
  // flight.setBookingType(BookingType.FREE);
  // }
  // });
  // }
  // } catch (Exception e) {
  // logger.error(e);
  // throw e;

  // } finally {
  // this.demoDataService.showBookingSummary();
  // }
  // }
}
