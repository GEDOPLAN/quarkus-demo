package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.service.FaultTolerantService;

import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("fault")
@Produces(MediaType.TEXT_PLAIN)
@ApplicationScoped
public class FaultTolerantResource {

  @Inject
  FaultTolerantService faultTolerantService;

  @GET
  public Response get() {
    return doGet(this.faultTolerantService::doSomething);
  }

  @GET
  @Path("retry")
  public Response getWithRetry() {
    return doGet(this.faultTolerantService::doSomethingWithRetry);
  }

  @GET
  @Path("timeout")
  public Response getWithTimeout() {
    return doGet(this.faultTolerantService::doSomethingWithTimeout);
  }

  @GET
  @Path("circuit")
  public Response getWithCircuitBreaker() {
    return doGet(this.faultTolerantService::doSomethingWithCircuitBreaker);
  }

  @GET
  @Path("fallback")
  public Response getWithFallback() {
    return doGet(this.faultTolerantService::doSomethingWithFallback);
  }

  private Response doGet(Supplier<Integer> function) {
    try {
      return Response.ok(function.get()).build();
    } catch (Exception e) {
      return Response.ok(e.toString()).build();
    }
  }
}
