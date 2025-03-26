package de.gedoplan.showcase.service;

import de.gedoplan.showcase.model.BookingType;
import de.gedoplan.showcase.model.Room;
import de.gedoplan.showcase.model.RoomBooking;
import de.gedoplan.showcase.persistence.RoomBookingRepository;
import de.gedoplan.showcase.persistence.RoomRepository;

import java.time.LocalDate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RoomBookingService {
  @Inject
  RoomRepository roomRepository;

  @Inject
  RoomBookingRepository roomBookingRepository;

  @Transactional
  public RoomBooking book(String location, int noOfSeats, LocalDate begin, int noOfDays, String reference, String lraId) {
    LocalDate end = begin.plusDays(noOfDays - 1);
    Room room = roomRepository.findAvailable(location, noOfSeats, begin, end)
      .stream()
      .min((r1, r2) -> Integer.compare(r1.getNoOfSeats(), r2.getNoOfSeats()))
      .orElseThrow(() -> new RoomUnavailableException(location, begin, noOfDays));

    RoomBooking roomBooking = new RoomBooking(room, begin, end, BookingType.BOOKED, reference, lraId);
    this.roomBookingRepository.persist(roomBooking);
    return roomBooking;
  }
}
