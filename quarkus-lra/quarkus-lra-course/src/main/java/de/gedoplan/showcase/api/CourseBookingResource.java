package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.RoomBookingService;
import de.gedoplan.showcase.service.TrainerBookingService;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
  @Produces(MediaType.TEXT_PLAIN)
  public String bookCourse(
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats) {

    String reference = UUID.randomUUID().toString();
    try {
      this.roomBookingService.bookRoom(location, noOfSeats, begin, noOfDays, reference);
    } catch (WebApplicationException e) {
      return String.format("Could not book room (Status %s)", e.getResponse().getStatusInfo().getReasonPhrase());
    }

    try {
      this.trainerBookingService.bookTrainer(course, begin, noOfDays, reference);
    } catch (WebApplicationException e) {
      return String.format("Could not book trainer (Status %s)", e.getResponse().getStatusInfo().getReasonPhrase());
    }

    return "OK";
  }
}
