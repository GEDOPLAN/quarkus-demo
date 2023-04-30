package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Patty;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("mock/stove")
public class StoveResourceMock {

  @Inject
  Logger logger;

  @Inject
  SlowDownService slowDownService;

  @POST
  @Path("patty")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Patty fryPattie(Patty patty) {
    this.logger.trace("[Mock] Fry pattie");
    this.slowDownService.delay(5000);
    patty.setFried(true);

    this.logger.trace("[Mock] Deliver pattie");
    return patty;
  }
}
