package de.gedoplan.showcase.api.provider;

import de.gedoplan.showcase.service.TrainerUnavailableException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TrainerUnavailableExceptionMapper implements ExceptionMapper<TrainerUnavailableException> {
  @Override
  public Response toResponse(TrainerUnavailableException exception) {
    return Response
      .status(Status.CONFLICT)
      .entity(exception.getMessage())
      .build();
  }
}
