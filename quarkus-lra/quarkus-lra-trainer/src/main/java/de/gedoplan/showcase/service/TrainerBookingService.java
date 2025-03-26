package de.gedoplan.showcase.service;

import de.gedoplan.showcase.model.BookingType;
import de.gedoplan.showcase.model.Trainer;
import de.gedoplan.showcase.model.TrainerBooking;
import de.gedoplan.showcase.persistence.TrainerBookingRepository;
import de.gedoplan.showcase.persistence.TrainerRepository;

import java.time.LocalDate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TrainerBookingService {
  @Inject
  TrainerRepository trainerRepository;

  @Inject
  TrainerBookingRepository trainerBookingRepository;

  @Transactional
  public TrainerBooking book(String course, LocalDate begin, int noOfDays, String reference, String lraId) {
    LocalDate end = begin.plusDays(noOfDays - 1);
    Trainer trainer = trainerRepository.findAvailable(course, begin, end)
      .stream()
      .findAny()
      .orElseThrow(() -> new TrainerUnavailableException(course, begin));

    TrainerBooking trainerBooking = new TrainerBooking(trainer, course, begin, end, BookingType.BOOKED, reference, lraId);
    this.trainerBookingRepository.persist(trainerBooking);
    return trainerBooking;
  }

  @Transactional
  public void cancel(String lraId) {
    this.trainerBookingRepository.findByLraId(lraId).ifPresent(trainerBookingRepository::remove);
  }
}
