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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.Response.Status.Family;
import jakarta.ws.rs.core.UriInfo;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
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
    @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference) {

    logger.debugf("Book trainer in LRA %s", shortenLraId(lraId));

    try {
      TrainerBooking booking = trainerBookingService.book(course, begin, noOfDays, reference, lraId);

      URI uri = this.uriInfo.getAbsolutePathBuilder().path(booking.getId().toString()).build();
      return Response.created(uri).build();
    } finally {
      showEngagedTrainers();
    }
  }

  @PUT
  @Path("compensate")
  @Compensate
  public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {

    logger.debugf("Cancel trainer booking in LRA %s", shortenLraId(lraId));

    try {
      this.trainerBookingService.cancel(lraId);
      return Response.ok().build();

    } catch (Exception e) {
      return Response.status(409).entity(ParticipantStatus.FailedToCompensate.name()).build();
    }finally {
      showEngagedTrainers();
    }
  }

  private static String shortenLraId(String lraId) {
    return lraId != null ? lraId.substring(lraId.lastIndexOf('/') + 1) : "null";
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
          shortenLraId(tb.getLraId())))
        .collect(Collectors.joining("\n ", "Engaged trainers:\n ", "")));
  }

}






