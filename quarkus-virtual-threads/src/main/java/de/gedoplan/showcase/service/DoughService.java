package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Dough;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@RegisterRestClient(configKey = "DoughService")
public interface DoughService {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Dough supplyDough(@QueryParam("type") String type, @QueryParam("weight") int weight);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<Dough> supplyDoughAsync(@QueryParam("type") String type, @QueryParam("weight") int weight);

}
