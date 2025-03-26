package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.RoomBookingService;
import de.gedoplan.showcase.service.TrainerBookingService;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.LRAStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
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
  @LRA(LRA.Type.REQUIRES_NEW)
  public void bookCourse(
    @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats) {

    logger.debugf("Starting LRA %s", lraId);

    String reference = UUID.randomUUID().toString();

    this.roomBookingService.bookRoom(location, noOfSeats, begin, noOfDays, reference);
    this.trainerBookingService.bookTrainer(course, begin, noOfDays, reference);
  }

  @AfterLRA
  @Path("/afterLRA")
  @PUT
  public Response afterLRA(@HeaderParam(LRA.LRA_HTTP_ENDED_CONTEXT_HEADER) URI lraId, LRAStatus status) {

    logger.debugf("after LRA %s (status=%s)", lraId, status);

    return Response.ok(status.name()).build();
  }

}
