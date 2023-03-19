package de.gedoplan.showcase.domain;

import lombok.Getter;

public enum PattyType {
  BEEF(false),
  PORK(false),
  QUINOA(true),
  TOFU(true);

  @Getter
  private boolean veggie;

  PattyType(boolean veggie) {
    this.veggie = veggie;
  }
}
