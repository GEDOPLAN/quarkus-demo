package de.gedoplan.showcase.service.mock;

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
@Path("mock/stove")
public class StoveResourceMock {

  @Inject
  Logger logger;

  @GET
  @Path("pattie/{veggie}")
  @Produces(MediaType.APPLICATION_JSON)
  public String prepareAndFryPattie(@PathParam("veggie") boolean veggie) {
    this.logger.trace("Fetch pattie");
    String pattie = veggie ? "Pattie (beans and corn)" : "Pattie (beef)";
    delay(500);

    this.logger.trace("Fry pattie");
    delay(4500);

    this.logger.trace("Deliver pattie");
    return pattie;
  }

  @SneakyThrows
  private static void delay(long millis) {
    Thread.sleep(millis);
  }
}
