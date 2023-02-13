package de.gedoplan.showcase.quarkus;

import io.quarkus.security.ForbiddenException;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;

/**
 * Mapper for ForbiddenException.
 *
 * Maps exception to
 * <ul>
 *   <li>a standard 403 response, if request was made for a REST resource,</li>
 *   <li>a redirect response for /forbidden.xhtml, if it was a request for a web app view.</li>
 * </ul>
 *
 * Notes:
 * <ul>
 *   <li>ForbiddenException is from io.quarkus.security, not javax.ws.rs!</li>
 *   <li>The redirect path is hard coded.</li>
 * </ul>
 */
@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
  @Context
  UriInfo uriInfo;

  @Inject
  Logger logger;

  @Override
  public Response toResponse(ForbiddenException e) {
    URI absoluteUri = uriInfo.getAbsolutePath();
    URI baseUri = uriInfo.getBaseUri();
    logger.debugf("absolutePath: %s", absoluteUri);
    logger.debugf("baseUri: %s", baseUri);

    // If the absolute path starts with the REST base path, it is a REST request
    boolean restRequest = absoluteUri.toString().startsWith(baseUri.toString());
    logger.debugf("restRequest: %s", restRequest);

    if (restRequest) {
      return Response.status(403).build();
    }

    URI redirectUri = URI.create(String.format("%s://%s:%d/forbidden.xhtml", absoluteUri.getScheme(), absoluteUri.getHost(), absoluteUri.getPort()));
    return Response.temporaryRedirect(redirectUri).build();
  }
}
