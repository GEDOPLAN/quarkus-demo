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

  @POST
  @Path("book")
  @Consumes("*/*")
  @LRA(value = LRA.Type.MANDATORY, end = false, cancelOnFamily = {})
  public Response bookRoom(
    @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference) {

    logger.debugf("Book room in LRA %s", shortenLraId(lraId));

    try {
      RoomBooking booking = roomBookingService.book(location, noOfSeats, begin, noOfDays, reference, lraId);

      URI uri = this.uriInfo.getAbsolutePathBuilder().path(booking.getId().toString()).build();
      return Response.created(uri).build();
    } finally {
      showUsedRooms();
    }
  }

  @PUT
  @Path("compensate")
  @Compensate
  public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {

    logger.debugf("Cancel room booking in LRA %s", shortenLraId(lraId));

    try {
      this.roomBookingService.cancel(lraId);
      return Response.ok().build();

    } catch (Exception e) {
      return Response.status(409).entity(ParticipantStatus.FailedToCompensate.name()).build();
    } finally {
      showUsedRooms();
    }

  }

  private static String shortenLraId(String lraId) {
    return lraId != null ? lraId.substring(lraId.lastIndexOf('/') + 1) : "null";
  }

  private void showUsedRooms() {
    this.logger.debug(
      this.roomBookingRepository
        .findAll()
        .stream()
        .map(tb -> String.format("%-22s %td.%<tm-%td.%<tm %-1.1s %s",
          tb.getRoom().getName(),
          tb.getBegin(),
          tb.getEnd(),
          tb.getBookingType(),
          shortenLraId(tb.getLraId())))
        .collect(Collectors.joining("\n ", "Used rooms:\n ", "")));
  }
}
