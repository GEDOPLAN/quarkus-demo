package de.gedoplan.showcase.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey = "FlightSingleStepBookingService")
@Path("single")
public interface FlightSingleStepBookingService {
  @PUT
  @Path("book/{whatToBook}")
  @Consumes("*/*")
  public void bookFlight(@PathParam("whatToBook") char whatToBook);

}
