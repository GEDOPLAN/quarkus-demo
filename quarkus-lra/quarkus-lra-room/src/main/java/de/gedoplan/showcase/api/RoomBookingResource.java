package de.gedoplan.showcase.api;

import de.gedoplan.showcase.model.RoomBooking;
import de.gedoplan.showcase.persistence.RoomBookingRepository;
import de.gedoplan.showcase.service.RoomBookingService;

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
@Path("room")
public class RoomBookingResource {
  @Inject
  RoomBookingRepository roomBookingRepository;

  @Inject
  RoomBookingService roomBookingService;

  @Context
  UriInfo uriInfo;

  @Inject
  Logger logger;

  @GET
  @Path("used")
  @Produces(MediaType.TEXT_PLAIN)
  public String getUsedRooms() {
    return this.roomBookingRepository
      .findAll()
      .stream()
      .map(tb -> String.format("%-35s  %-22s  %td.%<tm.%<ty-%td.%<tm.%<ty  %-8s  %s",
        tb.getReference(),
        tb.getRoom().getName(),
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
  public Response bookRoom(
    @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference) {

    logger.debugf("Book room in LRA %s", lraId);

    RoomBooking booking = roomBookingService.book(location, noOfSeats, begin, noOfDays, reference, lraId);

    URI uri = this.uriInfo.getAbsolutePathBuilder().path(booking.getId().toString()).build();
    return Response.created(uri).build();
  }

  @PUT
  @Path("compensate")
  @Compensate
  public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {

    logger.debugf("Cancel room booking in LRA %s", lraId);

    try {
      this.roomBookingService.cancel(lraId);
      return Response.ok(ParticipantStatus.Compensated.name()).build();

    } catch (Exception e) {
      return Response.ok(ParticipantStatus.FailedToCompensate.name()).build();
    }

  }

}
