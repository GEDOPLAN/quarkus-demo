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
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("burger/cs")
public class BurgerResourceCS {

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

    CompletionStage<Bun> bunCS = supplyDoughAsync(bunType)
      .thenCompose(this::bakeBunAsync);

    List<String> parts = bunCS
      .thenCombineAsync(prepareAndFryPattieAsync(veggie), (bun, pattie) -> {
        return List.of(
          bun.getUpperHalf(),
          this.miseEnPlaceService.getSauce(),
          this.miseEnPlaceService.getTomato(),
          this.miseEnPlaceService.getCheese(),
          pattie,
          this.miseEnPlaceService.getSalad(),
          bun.getLowerHalf()
        );
      })
      .toCompletableFuture()
      .get();

    this.logger.debugf("----- Deliver burger ------------------");
    return parts;
  }

  private CompletionStage<Dough> supplyDoughAsync(String bunType) {
    this.logger.debugf("Get dough");
    return this.doughService.supplyDoughAsync(bunType, 50);
  }

  private CompletionStage<Bun> bakeBunAsync(Dough dough) {
    this.logger.debugf("Bake bun");
    return this.ovenService.bakeBunAsync(dough);
  }

  private CompletionStage<String> prepareAndFryPattieAsync(boolean veggie) {
    this.logger.debugf("Request pattie");
    return this.stoveService.prepareAndFryPattieAsync(veggie);
  }
}
