package de.gedoplan.showcase.service;

import java.time.LocalDate;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(configKey = "TrainerBookingService")
@Path("trainer")
public interface TrainerBookingService {
  @POST
  @Path("book")
  @Consumes("*/*")
  public void bookTrainer(
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference);

}
