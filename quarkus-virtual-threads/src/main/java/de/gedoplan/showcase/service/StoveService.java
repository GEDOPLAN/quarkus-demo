package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Patty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@RegisterRestClient(configKey = "StoveService")
public interface StoveService {
  @POST
  @Path("patty")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Patty fryPattie(Patty patty);

  @POST
  @Path("patty")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<Patty> fryPattieAsync(Patty patty);

}
