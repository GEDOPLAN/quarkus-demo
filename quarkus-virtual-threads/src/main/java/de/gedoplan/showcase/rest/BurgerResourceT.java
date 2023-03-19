package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.domain.Dough;
import de.gedoplan.showcase.service.DoughService;
import de.gedoplan.showcase.service.MiseEnPlaceService;
import de.gedoplan.showcase.service.OvenService;
import de.gedoplan.showcase.service.StoveService;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@ApplicationScoped
@Path("burger/t")
public class BurgerResourceT {

  @Inject
  @RestClient
  DoughService doughService;

  @Inject
  @RestClient
  OvenService ovenService;

  @Inject
  @RestClient
  StoveService stoveService;

  @Inject
  MiseEnPlaceService miseEnPlaceService;

  @Inject
  Logger logger;

  @Inject
  ManagedExecutor executor;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("wheat") String bunType, @QueryParam("veggie") @DefaultValue("false") boolean veggie)
    throws ExecutionException, InterruptedException {

    this.logger.debugf("----- Start burger production ---------");

    Future<Bun> bunFuture = this.executor.submit(() -> bakeBun(supplyDough(bunType)));

    Future<String> pattieFuture = this.executor.submit(() -> prepareAndFryPattie(veggie));

    List<String> parts = List.of(
      bunFuture.get().getUpperHalf(),
      this.miseEnPlaceService.getSauce(),
      this.miseEnPlaceService.getTomato(),
      this.miseEnPlaceService.getCheese(),
      pattieFuture.get(),
      this.miseEnPlaceService.getSalad(),
      bunFuture.get().getLowerHalf());

    this.logger.debugf("----- Deliver burger ------------------");
    return parts;
  }

  private Dough supplyDough(String bunType) {
    this.logger.debugf("Get dough");
    return this.doughService.supplyDough(bunType, 50);
  }

  private Bun bakeBun(Dough dough) {
    this.logger.debugf("Bake bun");
    return this.ovenService.bakeBun(dough);
  }

  private String prepareAndFryPattie(boolean veggie) {
    this.logger.debugf("Request pattie");
    return this.stoveService.prepareAndFryPattie(veggie);
  }

}
