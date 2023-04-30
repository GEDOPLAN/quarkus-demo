package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Patty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@RegisterRestClient(configKey = "MeatService")
public interface MeatService {
  @GET
  @Path("patty")
  @Produces(MediaType.APPLICATION_JSON)
  public Patty supplyPattyMeat(@QueryParam("type") String type, @QueryParam("weight") int weight);

  @GET
  @Path("patty")
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<Patty> supplyPattyMeatAsync(@QueryParam("type") String type, @QueryParam("weight") int weight);

}
