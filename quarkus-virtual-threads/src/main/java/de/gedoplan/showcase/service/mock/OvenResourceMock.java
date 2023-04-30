package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.domain.Dough;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("mock/oven")
public class OvenResourceMock {

  @Inject
  Logger logger;

  @Inject
  SlowDownService slowDownService;

  @POST
  @Path("bun")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Bun bakeBun(Dough dough) {
    this.logger.trace("[Mock] Bake bun");
    this.slowDownService.delay(1500);

    this.logger.trace("[Mock] Cut bun in two halves");
    this.slowDownService.delay(500);

    this.logger.trace("[Mock] Deliver bun halves");
    return new Bun(dough.getType());
  }
}
