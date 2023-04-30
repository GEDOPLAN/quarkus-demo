package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.domain.Dough;
import de.gedoplan.showcase.domain.DoughType;
import de.gedoplan.showcase.domain.Patty;
import de.gedoplan.showcase.domain.PattyType;
import de.gedoplan.showcase.service.DoughService;
import de.gedoplan.showcase.service.MeatService;
import de.gedoplan.showcase.service.MiseEnPlaceService;
import de.gedoplan.showcase.service.OvenService;
import de.gedoplan.showcase.service.StoveService;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("cs/burger")
public class BurgerResourceCompletionStage {

  @Inject
  @RestClient
  DoughService doughService;

  @Inject
  @RestClient
  OvenService ovenService;

  @Inject
  @RestClient
  MeatService meatService;

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
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("WHEAT") DoughType bunType, @QueryParam("patty") @DefaultValue("BEEF") PattyType pattyType)
    throws ExecutionException, InterruptedException {

    this.logger.debugf("----- Start burger production ---------");

    List<String> parts =
      supplyDough(bunType).thenCompose(this::bakeBun)
        .thenCombine(
          pattyType.isVeggie()
            ? fryPattie(this.miseEnPlaceService.getVegetarianPatty(pattyType))
            : supplyPattyMeat(pattyType.toString()).thenCompose(this::fryPattie),
          (bun, pattie) -> {
            return List.of(
              bun.getUpperHalf(),
              this.miseEnPlaceService.getSauce(),
              this.miseEnPlaceService.getTomato(),
              this.miseEnPlaceService.getCheese(),
              pattie.toString(),
              this.miseEnPlaceService.getSalad(),
              bun.getLowerHalf()
            );
          })
        .toCompletableFuture()
        .get();

    this.logger.debugf("----- Deliver burger ------------------");
    return parts;
  }

  private CompletionStage<Dough> supplyDough(DoughType bunType) {
    this.logger.debugf("Get dough (%s)", bunType);
    return this.doughService.supplyDoughAsync(bunType, 50);
  }

  private CompletionStage<Bun> bakeBun(Dough dough) {
    this.logger.debugf("Bake bun (%s)", dough.getType());
    return this.ovenService.bakeBunAsync(dough);
  }

  private CompletionStage<Patty> supplyPattyMeat(String type) {
    this.logger.debugf("Get patty (%s)", type);
    return this.meatService.supplyPattyMeatAsync(type, 200);
  }

  private CompletionStage<Patty> fryPattie(Patty patty) {
    this.logger.debugf("Fry pattie (%s)", patty.getType());
    return this.stoveService.fryPattieAsync(patty);
  }
}
