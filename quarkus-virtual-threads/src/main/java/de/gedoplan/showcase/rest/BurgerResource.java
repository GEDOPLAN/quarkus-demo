package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.service.MiseEnPlaceService;
import de.gedoplan.showcase.service.StoveService;
import de.gedoplan.showcase.service.ToasterService;
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
@Path("burger")
public class BurgerResource {

  @Inject
  @RestClient
  ToasterService toasterService;

  @Inject
  @RestClient
  StoveService stoveService;

  @Inject
  MiseEnPlaceService miseEnPlaceService;

  @Inject
  Logger logger;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("wheat") String bunType, @QueryParam("veggie") @DefaultValue("false") boolean veggie) {

    this.logger.debugf("----- Start burger production ---------");

    this.logger.debugf("Request bun");
    Bun bun = this.toasterService.cutAndToastBun(bunType);

    this.logger.debugf("Request pattie");
    String pattie = this.stoveService.prepareAndFryPattie(veggie);

    this.logger.debugf("----- Assemble and deliver burger -----");
    return List.of(
      bun.getUpperHalf(),
      this.miseEnPlaceService.getSauce(),
      this.miseEnPlaceService.getTomato(),
      this.miseEnPlaceService.getCheese(),
      pattie,
      this.miseEnPlaceService.getSalad(),
      bun.getLowerHalf());
  }

  @GET
  @Path("async")
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getBurgerAsync(@QueryParam("bun") @DefaultValue("wheat") String bunType, @QueryParam("veggie") @DefaultValue("false") boolean veggie)
    throws ExecutionException, InterruptedException {

    this.logger.debugf("----- Start burger production ---------");

    return cutAndToastBunAsync(bunType)
      .thenCombineAsync(prepareAndFryPattieAsync(veggie), (bun, pattie) -> {
        this.logger.debugf("Assemble burger");
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
  }

  private CompletionStage<Bun> cutAndToastBunAsync(String bunType) {
    this.logger.debugf("Request bun");
    return this.toasterService.cutAndToastBunAsync(bunType);
  }

  private CompletionStage<String> prepareAndFryPattieAsync(boolean veggie) {
    this.logger.debugf("Request pattie");
    return this.stoveService.prepareAndFryPattieAsync(veggie);
  }
}
