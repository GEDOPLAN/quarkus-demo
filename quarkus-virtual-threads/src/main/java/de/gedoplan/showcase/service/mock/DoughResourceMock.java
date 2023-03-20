package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Dough;
import de.gedoplan.showcase.domain.DoughType;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
