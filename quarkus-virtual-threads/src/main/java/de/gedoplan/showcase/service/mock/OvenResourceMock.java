package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.domain.Dough;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("mock/oven")
public class OvenResourceMock {

  @Inject
  Logger logger;

  @POST
  @Path("bun")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Bun bakeBun(Dough dough) {
    this.logger.trace("[Mock] Bake bun");
    delay(1500);

    this.logger.trace("[Mock] Cut bun in two halves");
    delay(500);

    this.logger.trace("[Mock] Deliver bun halves");
    return new Bun(dough.getDoughType());
  }

  @SneakyThrows
  private static void delay(long millis) {
    Thread.sleep(millis);
  }
}
