package de.gedoplan.showcase.service;

import java.time.LocalDate;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "TrainerBookingService")
@Path("trainer-booking")
public interface TrainerBookingService {
  @POST
  @Consumes("*/*")
  public void bookTrainer(
    @QueryParam("course") String course,
    @QueryParam("begin") LocalDate begin,
    @QueryParam("noOfDays") int noOfDays,
    @QueryParam("reference") String reference);

}
