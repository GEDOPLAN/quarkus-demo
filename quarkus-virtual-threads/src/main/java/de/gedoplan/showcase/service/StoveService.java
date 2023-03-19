package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Patty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
