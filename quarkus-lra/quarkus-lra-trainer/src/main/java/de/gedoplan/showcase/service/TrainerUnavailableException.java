package de.gedoplan.showcase.service;

import java.time.LocalDate;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

public class TrainerUnavailableException extends RuntimeException {
  public TrainerUnavailableException(String course, LocalDate begin) {
    super(String.format("No trainer available for course %s starting on %s", course, begin));
  }
}
