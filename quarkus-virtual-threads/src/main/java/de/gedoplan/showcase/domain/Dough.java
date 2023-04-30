package de.gedoplan.showcase.domain;

import lombok.Getter;
import lombok.ToString;

import jakarta.json.bind.annotation.JsonbCreator;

@Getter
@ToString
public class Dough {
  private DoughType type;
  private int weight;

  @JsonbCreator
  public Dough(DoughType type, int weight) {
    this.type = type;
    this.weight = weight;
  }
}
