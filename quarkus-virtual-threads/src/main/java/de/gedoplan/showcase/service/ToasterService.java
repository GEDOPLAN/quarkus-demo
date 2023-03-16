package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Bun;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "ToasterService")
public interface ToasterService {
  @GET
  @Path("bun/{bunType}")
  @Produces(MediaType.APPLICATION_JSON)
  public Bun cutAndToastBun(@PathParam("bunType") String bunType);
}
