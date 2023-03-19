package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Patty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
