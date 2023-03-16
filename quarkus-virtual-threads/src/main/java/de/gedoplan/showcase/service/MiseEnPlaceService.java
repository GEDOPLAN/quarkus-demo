package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;

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
}
