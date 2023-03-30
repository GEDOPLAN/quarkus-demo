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
@Path("seq/burger")
public class BurgerResourceSequential {

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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("WHEAT") DoughType bunType, @QueryParam("patty") @DefaultValue("BEEF") PattyType pattyType) {

    this.logger.debugf("----- Start burger production ---------");

    Bun bun = bakeBun(supplyBunDough(bunType));

    Patty patty = pattyType.isVeggie()
      ? this.miseEnPlaceService.getVegetarianPatty(pattyType)
      : supplyPattyMeat(pattyType.toString());
    patty = fryPattie(patty);

    List<String> parts = List.of(
      bun.getUpperHalf(),
      this.miseEnPlaceService.getSauce(),
      this.miseEnPlaceService.getTomato(),
      this.miseEnPlaceService.getCheese(),
      patty.toString(),
      this.miseEnPlaceService.getSalad(),
      bun.getLowerHalf());

    this.logger.debugf("----- Deliver burger ------------------");
    return parts;
  }

  private Dough supplyBunDough(DoughType bunType) {
    this.logger.debugf("Get dough (%s)", bunType);
    return this.doughService.supplyBunDough(bunType, 50);
  }

  private Bun bakeBun(Dough dough) {
    this.logger.debugf("Bake bun (%s)", dough.getType());
    return this.ovenService.bakeBun(dough);
  }

  private Patty supplyPattyMeat(String meatType) {
    this.logger.debugf("Get patty (%s)", meatType);
    return this.meatService.supplyPattyMeat(meatType, 200);
  }

  private Patty fryPattie(Patty patty) {
    this.logger.debugf("Fry pattie (%s)", patty.getType());
    return this.stoveService.fryPattie(patty);
  }
}
