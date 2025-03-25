package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.RoomBookingService;
import de.gedoplan.showcase.service.TrainerBookingService;

import java.time.LocalDate;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("course-booking")
public class CourseBookingResource {

  @Inject
  @RestClient
  RoomBookingService roomBookingService;

  @Inject
  @RestClient
  TrainerBookingService trainerBookingService;

  @Inject
  Logger logger;

  @POST
  @Consumes("*/*")
  public void bookCourse(
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats) {
    this.roomBookingService.bookRoom(location, noOfSeats, begin, noOfDays);
    this.trainerBookingService.bookTrainer(course, begin, noOfDays);
  }
}
