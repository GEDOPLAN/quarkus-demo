package de.gedoplan.showcase.domain;

import lombok.Getter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbCreator;

@Getter
@ToString
public class Dough {
  private String doughType;
  private int weight;

  @JsonbCreator
  public Dough(String doughType, int weight) {
    this.doughType = doughType;
    this.weight = weight;
  }
}
