package de.gedoplan.showcase.api;

import java.net.URI;

import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.LRAStatus;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import de.gedoplan.showcase.service.SingleStepFlightBookingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("single")
public class SingleStepTravelBookingResource {

  @Inject
  @RestClient
  SingleStepFlightBookingService flightSingleStepBookingService;

  @Inject
  Logger logger;

  @PUT
  @Path("book/{whatToBook}")
  @Consumes("*/*")
  @LRA(LRA.Type.REQUIRES_NEW)
  public void book(@PathParam("whatToBook") String whatToBook, @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
    logger.debugf("Starting LRA %s", lraId);

    whatToBook
        .chars()
        .forEach(x -> book((char) x));
  }

  private void book(char whatToBook) {
    if (Character.isAlphabetic(whatToBook))
      bookFlight(whatToBook);
    else if (Character.isDigit(whatToBook))
      bookHotel(whatToBook);
    else
      throw new BadRequestException();
  }

  private void bookFlight(char whatToBook) {
    this.flightSingleStepBookingService.bookFlight(whatToBook);
  }

  private void bookHotel(char whatToBook) {
    logger.debugf("bookHotel(%c)", whatToBook);
  }

  @AfterLRA
  @Path("/afterLRA")
  @PUT
  public Response afterLRA(@HeaderParam(LRA.LRA_HTTP_ENDED_CONTEXT_HEADER) URI lraId, LRAStatus status) {

    logger.debugf("afterLRA: lraId=%s, status=%s", lraId, status);

    return Response.ok(status.name()).build();
  }
}
