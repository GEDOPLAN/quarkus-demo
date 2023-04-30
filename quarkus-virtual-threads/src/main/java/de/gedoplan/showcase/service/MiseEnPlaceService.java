package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Patty;
import de.gedoplan.showcase.domain.PattyType;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MiseEnPlaceService {

  public String getSauce() {
    return "BBQ sauce";
  }

  public String getTomato() {
    return "Tomato slice";
  }

  public String getCheese() {
    return "Cheese slice";
  }

  public String getSalad() {
    return "Salad leaf";
  }

  public Patty getVegetarianPatty(PattyType type) {
    return new Patty(type);
  }
}
