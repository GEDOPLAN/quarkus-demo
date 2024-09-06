package de.gedoplan.showcase.api;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import de.gedoplan.showcase.service.FlightService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("single")
@Transactional
public class SingleStepFlightBookingResource {

  @Inject
  FlightService flightService;

  @PUT
  @Path("book/{whatToBook}")
  @Consumes("*/*")
  @LRA(value = LRA.Type.MANDATORY, end = false)
  public Response bookFlight(@PathParam("whatToBook") char whatToBook, @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
    this.flightService.bookFlight(whatToBook, lraId);

    return Response.ok().build();
  }

  @PUT
  @Path("compensate")
  @Compensate
  public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
    try {
      this.flightService.unbookFlights(lraId);
      return Response.ok(ParticipantStatus.Compensated.name()).build();

    } catch (Exception e) {
      return Response.ok(ParticipantStatus.FailedToCompensate.name()).build();
    }

  }

}
