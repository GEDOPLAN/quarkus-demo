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

    Bun bun = cutAndToastBun(bunType);

    String pattie = prepareAndFryPattie(veggie);

    List<String> parts = List.of(
      bun.getUpperHalf(),
      this.miseEnPlaceService.getSauce(),
      this.miseEnPlaceService.getTomato(),
      this.miseEnPlaceService.getCheese(),
      pattie,
      this.miseEnPlaceService.getSalad(),
      bun.getLowerHalf());

    this.logger.debugf("----- Deliver burger ------------------");
    return parts;
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
