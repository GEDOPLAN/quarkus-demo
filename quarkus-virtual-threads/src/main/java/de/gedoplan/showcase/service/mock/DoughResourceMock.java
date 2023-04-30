package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Dough;
import de.gedoplan.showcase.domain.DoughType;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("mock/dough")
public class DoughResourceMock {

  @Inject
  Logger logger;

  @Inject
  SlowDownService slowDownService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Dough supplyDough(@QueryParam("type") DoughType type, @QueryParam("weight") int weight) {
    this.logger.trace("[Mock] Fetch dough from fridge");
    this.slowDownService.delay(250);

    this.logger.trace("[Mock] Knead dough");
    this.slowDownService.delay(500);

    this.logger.trace("[Mock] Deliver dough");
    return new Dough(type, weight);
  }
}
