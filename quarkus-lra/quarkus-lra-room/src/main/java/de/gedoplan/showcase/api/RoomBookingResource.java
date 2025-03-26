package de.gedoplan.showcase.api;

import de.gedoplan.showcase.model.Room;
import de.gedoplan.showcase.model.RoomBooking;
import de.gedoplan.showcase.persistence.RoomBookingRepository;
import de.gedoplan.showcase.persistence.RoomRepository;
import de.gedoplan.showcase.service.RoomBookingService;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("room-booking")
public class RoomBookingResource {
  @Inject
  RoomBookingRepository roomBookingRepository;

  @Inject
  RoomBookingService roomBookingService;

  @Context
  UriInfo uriInfo;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getAll() {
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
  @Consumes("*/*")
  public Response createBooking(
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference) {

    RoomBooking booking = roomBookingService.book(location, noOfSeats, begin, noOfDays, reference, null);

    URI uri = this.uriInfo.getAbsolutePathBuilder().path(booking.getId().toString()).build();
    return Response.created(uri).build();
  }
}
