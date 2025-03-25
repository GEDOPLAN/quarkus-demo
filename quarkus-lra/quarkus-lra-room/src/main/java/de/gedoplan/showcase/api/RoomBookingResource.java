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
@Produces(MediaType.APPLICATION_JSON)
public class RoomBookingResource {
  @Inject
  RoomBookingRepository roomBookingRepository;

  @Inject
  RoomBookingService roomBookingService;

  @Context
  UriInfo uriInfo;

  @GET
  public List<RoomBooking> getAll() {
    return this.roomBookingRepository.findAll();
  }

  @POST
  @Consumes("*/*")
  public Response createBooking(
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays) {

    RoomBooking booking = roomBookingService.book(location, noOfSeats, begin, noOfDays, null);

    URI uri = this.uriInfo.getAbsolutePathBuilder().path(booking.getId().toString()).build();
    return Response.created(uri).build();
  }
}
