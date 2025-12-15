package de.gedoplan.showcase.service;

import java.time.LocalDate;

public class TrainerUnavailableException extends RuntimeException {
  public TrainerUnavailableException(String course, LocalDate begin) {
    super(String.format("No trainer available for course %s starting on %s", course, begin));
  }
}
