package de.gedoplan.showcase.service;

import org.jboss.logging.Logger;

import de.gedoplan.showcase.model.BookingType;
import de.gedoplan.showcase.persistence.HotelRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class HotelService {

  @Inject
  HotelRepository hotelRepository;

  @Inject
  DemoDataService demoDataService;

  @Inject
  Logger logger;

  public void bookHotel(char whatToBook, String lraId) {
    logger.debugf("bookHotel(%c, %s)", whatToBook, lraId);

    try {
      var hotel = this.hotelRepository
          .findById(whatToBook)
          .orElseThrow(() -> new NotFoundException("Hotel " + whatToBook + " not found"));

      if (hotel.getBookingType() != BookingType.FREE)
        throw new BadRequestException("Hotel " + whatToBook + " is not available anymore");

      hotel.setBookingType(BookingType.BOOKED);
      hotel.setLraId(lraId);

    } catch (Exception e) {
      logger.error(e);
      throw e;

    } finally {
      this.demoDataService.showBookingSummary();
    }
  }

  public void unbookHotels(String lraId) {
    logger.debugf("unbookHotels(%s)", lraId);

    try {
      this.hotelRepository
          .findByLraId(lraId)
          .forEach(hotel -> {
            hotel.setLraId(null);
            hotel.setBookingType(BookingType.FREE);
      });
    } catch (Exception e) {
      logger.error(e);
      throw e;

    } finally {
      this.demoDataService.showBookingSummary();
    }
  }

}
