package de.gedoplan.showcase.service;

import java.time.LocalDate;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(configKey = "RoomBookingService")
@Path("room")
public interface RoomBookingService {
  @POST
  @Path("book")
  @Consumes("*/*")
  public void bookRoom(
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference);

}
