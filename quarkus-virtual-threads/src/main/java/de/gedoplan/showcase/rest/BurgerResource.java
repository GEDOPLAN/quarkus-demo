package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.service.MiseEnPlaceService;
import de.gedoplan.showcase.service.StoveService;
import de.gedoplan.showcase.service.ToasterService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("wheat") String bunType, @QueryParam("veggie") @DefaultValue("false") boolean veggie) {

    List<String> parts = new ArrayList<>();

    Bun bun = this.toasterService.cutAndToastBun(bunType);
    String sauce = this.miseEnPlaceService.getSauce();
    String tomato = this.miseEnPlaceService.getTomato();
    String cheese = this.miseEnPlaceService.getCheese();
    String pattie = this.stoveService.prepareAndFryPattie(veggie);
    String salad = this.miseEnPlaceService.getSalad();

    parts.add(bun.getUpperHalf());
    parts.add(sauce);
    parts.add(tomato);
    parts.add(cheese);
    parts.add(pattie);
    parts.add(salad);
    parts.add(bun.getLowerHalf());

    return parts;
  }
}
