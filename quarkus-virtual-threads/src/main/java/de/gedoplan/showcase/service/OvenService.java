package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.domain.Dough;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@RegisterRestClient(configKey = "OvenService")
public interface OvenService {
  @POST
  @Path("bun")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Bun bakeBun(Dough dough);

  @POST
  @Path("bun")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<Bun> bakeBunAsync(Dough dough);

}
