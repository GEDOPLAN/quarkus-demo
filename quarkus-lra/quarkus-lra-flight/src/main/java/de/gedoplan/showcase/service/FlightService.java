package de.gedoplan.showcase.service;

import java.util.Map;
import java.util.TreeMap;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FlightService {

  @Inject
  Logger logger;

  private Map<Character, Booking> flights = new TreeMap<>() {
    {
      put('a', new Booking());
      put('b', new Booking());
      put('c', new Booking());
    }
  };

  public synchronized void bookFlight(char whatToBook) {
    logger.debugf("bookFlight(%c)", whatToBook);

    try {
      Booking booking = this.flights.get(whatToBook);
      if (booking == null)
        throw new NotFoundException("Flight " + whatToBook + " not found");

      if (booking.getType() != BookingType.FREE)
        throw new BadRequestException("Flight " + whatToBook + " is not available anymore");

      booking.setType(BookingType.BOOKED);
    } catch (Exception e) {
      logger.error(e);
      throw e;
      
    } finally {
      showAll();
    }
  }

  private void showAll() {
    logger.debug("Flight booking summary:");
    flights.forEach((id, booking) -> logger.debugf("  Flight %s: %s (%s)", id, booking.getType(), booking.getLraId()));
  }
}
