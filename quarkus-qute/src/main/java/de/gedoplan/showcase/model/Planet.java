package de.gedoplan.showcase.model;

import io.quarkus.qute.TemplateData;
import io.quarkus.qute.TemplateExtension;

/**
 * Model class representing a planet in the solar system.
 */
@TemplateData
public record Planet(String name, double diameterKm, double distanceFromSunMillionKm,
                     boolean hasRings, int numberOfMoons, String description) {
  public Planet() {
    this(null, 0.0, 0.0, false, 0, null);
  }

  public Planet withName(String name) {
    return new Planet(name, this.diameterKm(), this.distanceFromSunMillionKm(),
        this.hasRings(), this.numberOfMoons(), this.description());
  }
}