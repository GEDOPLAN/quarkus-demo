package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Patty;
import de.gedoplan.showcase.domain.PattyType;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("mock/meat")
public class MeatResourceMock {

  @Inject
  Logger logger;

  @Inject
  SlowDownService slowDownService;

  @GET
  @Path("patty")
  @Produces(MediaType.APPLICATION_JSON)
  public Patty supplyPatty(@QueryParam("type") String type, @QueryParam("weight") int weight) {
    this.logger.trace("[Mock] Fetch meat from fridge");
    this.slowDownService.delay(250);

    this.logger.trace("[Mock] Mince meat");
    this.slowDownService.delay(1000);

    this.logger.trace("[Mock] Deliver minced meat");
    return new Patty(PattyType.valueOf(type), true);
  }
}
