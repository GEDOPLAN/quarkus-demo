package de.gedoplan.showcase.service;

import java.time.LocalDate;

public class RoomUnavailableException extends RuntimeException {
  public RoomUnavailableException(String location, LocalDate begin, int noOfDays) {
    super(String.format("No room available in %s for %d days starting on %s", location, noOfDays, begin));
  }
}
