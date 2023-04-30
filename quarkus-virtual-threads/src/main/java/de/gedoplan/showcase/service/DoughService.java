package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Dough;
import de.gedoplan.showcase.domain.DoughType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@RegisterRestClient(configKey = "DoughService")
public interface DoughService {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Dough supplyBunDough(@QueryParam("type") DoughType type, @QueryParam("weight") int weight);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<Dough> supplyDoughAsync(@QueryParam("type") DoughType type, @QueryParam("weight") int weight);

}
