package de.gedoplan.showcase.service;

import java.time.LocalDate;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "RoomBookingService")
@Path("room-booking")
public interface RoomBookingService {
  @POST
  @Consumes("*/*")
  public Response bookRoom(
    @QueryParam("location") String location,
    @QueryParam("noOfSeats") int noOfSeats,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays);

}
