package de.gedoplan.showcase.domain;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbCreator;

@Getter
public class Patty {
  private PattyType type;

  @Setter
  private boolean fried;

  public Patty(PattyType type) {
    this(type, false);
  }

  @JsonbCreator
  public Patty(PattyType type, boolean fried) {
    this.type = type;
    this.fried = fried;
  }

  @Override
  public String toString() {
    return "Patty (" + this.type + ", " + (this.fried ? "fried" : "raw") + ')';
  }
}
