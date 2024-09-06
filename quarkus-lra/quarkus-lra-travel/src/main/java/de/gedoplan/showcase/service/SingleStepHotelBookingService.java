package de.gedoplan.showcase.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey = "SingleStepHotelBookingService")
@Path("single")
public interface SingleStepHotelBookingService {
  @PUT
  @Path("book/{whatToBook}")
  @Consumes("*/*")
  public void bookHotel(@PathParam("whatToBook") char whatToBook);

}
