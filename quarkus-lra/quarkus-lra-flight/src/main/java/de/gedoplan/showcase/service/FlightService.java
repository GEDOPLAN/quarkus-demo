package de.gedoplan.showcase.service;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

  public synchronized void bookFlight(char whatToBook, String lraId) {
    logger.debugf("bookFlight(%c, %s)", whatToBook, lraId);

    try {
      Booking booking = this.flights.get(whatToBook);
      if (booking == null)
        throw new NotFoundException("Flight " + whatToBook + " not found");

      if (booking.getType() != BookingType.FREE)
        throw new BadRequestException("Flight " + whatToBook + " is not available anymore");

      booking.setType(BookingType.BOOKED);
      booking.setLraId(lraId);
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

  public Set<Character> getBookedFlightsOfLra(String lraId) {
    if (lraId == null)
      return Set.of();

    return this.flights
        .entrySet()
        .stream()
        .filter(e -> lraId.equals(e.getValue().getLraId()))
        .map(e -> e.getKey())
        .collect(Collectors.toSet());
  }

  public synchronized void unbookFlight(char whatToUnbook, String lraId) {
    logger.debugf("unbookFlight(%c, %s)", whatToUnbook, lraId);

    if (lraId != null) {

      Booking booking = this.flights.get(whatToUnbook);
      if (booking != null && lraId.equals(booking.getLraId())) {
        booking.setType(BookingType.FREE);
        booking.setLraId(null);
      }

      showAll();
    }
  }
}
