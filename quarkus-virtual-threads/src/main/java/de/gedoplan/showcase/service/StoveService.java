package de.gedoplan.showcase.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@RegisterRestClient(configKey = "StoveService")
public interface StoveService {
  @GET
  @Path("pattie/{veggie}")
  @Produces(MediaType.APPLICATION_JSON)
  public String prepareAndFryPattie(@PathParam("veggie") boolean veggie);

  @GET
  @Path("pattie/{veggie}")
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<String> prepareAndFryPattieAsync(@PathParam("veggie") boolean veggie);

}
