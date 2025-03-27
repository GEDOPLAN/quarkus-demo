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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.LRAStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("course")
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
  @Path("book")
  @Consumes("*/*")
  @Produces(MediaType.TEXT_PLAIN)
  @LRA(LRA.Type.REQUIRES_NEW)
  public Response bookCourse(
    @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats) {

    logger.debugf("LRA %s: start", getShortLraId(lraId));

    String reference = UUID.randomUUID().toString();

    try {
      this.roomBookingService.bookRoom(location, noOfSeats, begin, noOfDays, reference);
    } catch (Exception e) {
      logger.debugf("LRA %s: error booking room", getShortLraId(lraId));
      return Response.status(400).build();
    }

    try {
      this.trainerBookingService.bookTrainer(course, begin, noOfDays, reference);
    } catch (Exception e) {
      logger.debugf("LRA %s: error booking trainer", getShortLraId(lraId));
      return Response.status(400).build();
    }

    return Response.ok().build();
  }

  @Complete
  @Path("/complete")
  @PUT
  public Response complete(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {

    logger.debugf("LRA %s: complete", getShortLraId(lraId));

    return Response.ok().build();
  }

  @Compensate
  @Path("/compensate")
  @PUT
  public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {

    logger.debugf("LRA %s: compensate", getShortLraId(lraId));

    return Response.ok().build();
  }

  @AfterLRA
  @Path("/afterLRA")
  @PUT
  public Response afterLRA(@HeaderParam(LRA.LRA_HTTP_ENDED_CONTEXT_HEADER) URI lraId, LRAStatus status) {

    logger.debugf("LRA %s: final state: %s", getShortLraId(lraId), status);

    return Response.ok().build();
  }

  private static String getShortLraId(URI lraId) {
    if (lraId == null)
      return "null";

    String lraIdString = lraId.toString();
    return lraIdString.substring(lraIdString.lastIndexOf('/') + 1);
  }

}
