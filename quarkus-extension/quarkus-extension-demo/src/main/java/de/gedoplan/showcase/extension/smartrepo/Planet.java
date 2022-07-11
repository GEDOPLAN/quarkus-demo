package de.gedoplan.showcase.extension.smartrepo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Planet {
  @GeneratedValue
  @Id
  private Long id;

  private String name;
  private double mass;

  public Planet(String name, double mass) {
    this.name = name;
    this.mass = mass;
  }

  protected Planet() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getMass() {
    return mass;
  }
}
