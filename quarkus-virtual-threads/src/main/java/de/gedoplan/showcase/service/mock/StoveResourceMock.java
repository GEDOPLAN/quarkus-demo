package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Patty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
