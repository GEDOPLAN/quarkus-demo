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
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

  @GET
  @Path("engaged")
  @Produces(MediaType.TEXT_PLAIN)
  public String getEngagedTrainers() {
    return this.trainerBookingRepository
      .findAll()
      .stream()
      .map(tb -> String.format("%-35s  %-18s  %-3s  %td.%<tm.%<ty-%td.%<tm.%<ty  %-8s  %s",
        tb.getReference(),
        tb.getTrainer().getName(),
        tb.getCourse(),
        tb.getBegin(),
        tb.getEnd(),
        tb.getBookingType(),
        tb.getLraId()))
      .collect(Collectors.joining("\n", "", ""));
  }

  @POST
  @Path("book")
  @Consumes("*/*")
  @LRA(value = LRA.Type.MANDATORY, end = false)
  public Response bookTrainer(
    @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference) {

    logger.debugf("Book trainer in LRA %s", lraId);

    TrainerBooking booking = trainerBookingService.book(course, begin, noOfDays, reference, lraId);

    URI uri = this.uriInfo.getAbsolutePathBuilder().path(booking.getId().toString()).build();
    return Response.created(uri).build();
  }

  @PUT
  @Path("compensate")
  @Compensate
  public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {

    logger.debugf("Cancel trainer booking in LRA %s", lraId);

    try {
      this.trainerBookingService.cancel(lraId);
      return Response.ok(ParticipantStatus.Compensated.name()).build();

    } catch (Exception e) {
      return Response.ok(ParticipantStatus.FailedToCompensate.name()).build();
    }
  }
}






