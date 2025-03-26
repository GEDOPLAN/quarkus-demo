package de.gedoplan.showcase.api.provider;

import de.gedoplan.showcase.service.RoomUnavailableException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RoomUnavailableExceptionMapper implements ExceptionMapper<RoomUnavailableException> {
  @Override
  public Response toResponse(RoomUnavailableException exception) {
    return Response
      .status(Status.CONFLICT)
      .entity(exception.getMessage())
      .build();
  }
}
