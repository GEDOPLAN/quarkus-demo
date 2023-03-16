package de.gedoplan.showcase.service.mock;

import de.gedoplan.showcase.domain.Bun;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("mock/toaster")
public class ToasterResourceMock {

  @Inject
  Logger logger;

  @GET
  @Path("bun/{bunType}")
  @Produces(MediaType.APPLICATION_JSON)
  public Bun cutAndToastBun(@PathParam("bunType") String bunType) {
    this.logger.debug("Fetch bun");
    Bun bun = new Bun(bunType);
    delay(500);

    this.logger.debug("Toast bun");
    delay(1500);

    this.logger.debug("Deliver bun");
    return bun;
  }

  @SneakyThrows
  private static void delay(long millis) {
    Thread.sleep(millis);
  }
}
