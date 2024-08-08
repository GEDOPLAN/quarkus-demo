package de.gedoplan.showcase.api;

import org.jboss.logging.Logger;

import de.gedoplan.showcase.service.FlightService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("single")
public class SingleStepBookingResource {

  @Inject
  FlightService flightService;

  @PUT
  @Path("book/{whatToBook}")
  @Consumes("*/*")
  public void bookFlight(@PathParam("whatToBook") char whatToBook) {
    this.flightService.bookFlight(whatToBook);
  }

}
