package de.gedoplan.showcase.api;

import de.gedoplan.showcase.model.TrainerBooking;
import de.gedoplan.showcase.persistence.TrainerBookingRepository;
import de.gedoplan.showcase.service.TrainerBookingService;

import java.net.URI;
import java.time.LocalDate;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.jboss.logging.Logger;

@ApplicationScoped
@Path("trainer")
public class TrainerBookingResource {
  @Inject
  TrainerBookingRepository trainerBookingRepository;

  @Inject
  TrainerBookingService trainerBookingService;

  @Context
  UriInfo uriInfo;

  @Inject
  Logger logger;

  @POST
  @Path("book")
  @Consumes("*/*")
  @LRA(value = LRA.Type.MANDATORY, end = false, cancelOnFamily = {})
  public Response bookTrainer(
    @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference) {

    logger.debugf("Book trainer in LRA %s", getShortLraId(lraId));

    try {
      TrainerBooking booking = trainerBookingService.book(course, begin, noOfDays, reference, lraId.toString());

      URI uri = this.uriInfo.getAbsolutePathBuilder().path(booking.getId().toString()).build();
      return Response.created(uri).build();
    } finally {
      showEngagedTrainers();
    }
  }

  @Compensate
  public void compensate(URI lraId) {

    logger.debugf("Cancel trainer booking in LRA %s", getShortLraId(lraId));

    try {
      this.trainerBookingService.cancel(lraId.toString());
    }finally {
      showEngagedTrainers();
    }
  }

  private static String getShortLraId(Object lraId) {
    if (lraId == null)
      return "null";

    String lraIdString = lraId.toString();
    return lraIdString.substring(lraIdString.lastIndexOf('/') + 1);
  }

  private void showEngagedTrainers() {
    this.logger.debug(
      this.trainerBookingRepository
        .findAll()
        .stream()
        .map(tb -> String.format("%-18s %-3s %td.%<tm-%td.%<tm %-1.1s %s",
          tb.getTrainer().getName(),
          tb.getCourse(),
          tb.getBegin(),
          tb.getEnd(),
          tb.getBookingType(),
          getShortLraId(tb.getLraId())))
        .collect(Collectors.joining("\n ", "Engaged trainers:\n ", "")));
  }

}






