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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

@ApplicationScoped
@Path("burger/vt")
public class BurgerResourceVT {

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
  //  @RunOnVirtualThread
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("wheat") String bunType, @QueryParam("veggie") @DefaultValue("false") boolean veggie)
    throws ExecutionException, InterruptedException {

    this.logger.debugf("----- Start burger production ---------");

    final ThreadFactory factory = Thread.ofVirtual().name("virtual-", 1).factory();
    ExecutorService executor = Executors.newThreadPerTaskExecutor(factory);

    Future<Bun> bunFuture = executor.submit(() -> cutAndToastBun(bunType));

    Future<String> pattieFuture = executor.submit(() -> prepareAndFryPattie(veggie));

    this.logger.debugf("----- Assemble and deliver burger -----");
    return List.of(
      bunFuture.get().getUpperHalf(),
      this.miseEnPlaceService.getSauce(),
      this.miseEnPlaceService.getTomato(),
      this.miseEnPlaceService.getCheese(),
      pattieFuture.get(),
      this.miseEnPlaceService.getSalad(),
      bunFuture.get().getLowerHalf());
  }

  private Bun cutAndToastBun(String bunType) {
    this.logger.debugf("Request bun");
    return this.toasterService.cutAndToastBun(bunType);
  }

  private String prepareAndFryPattie(boolean veggie) {
    this.logger.debugf("Request pattie");
    return this.stoveService.prepareAndFryPattie(veggie);
  }

}
