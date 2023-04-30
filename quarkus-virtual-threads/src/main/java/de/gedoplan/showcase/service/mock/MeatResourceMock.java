package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Patty;
import de.gedoplan.showcase.domain.PattyType;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

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
