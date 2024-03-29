package de.gedoplan.showcase.domain;

import lombok.Getter;
import lombok.ToString;

import jakarta.json.bind.annotation.JsonbCreator;

@Getter
@ToString
public class Bun {
  private String upperHalf;
  private String lowerHalf;

  public Bun(DoughType bunType) {
    this("Bun (" + bunType + ", upper half)", "Bun (" + bunType + ", lower half)");
  }

  @JsonbCreator
  public Bun(String upperHalf, String lowerHalf) {
    this.upperHalf = upperHalf;
    this.lowerHalf = lowerHalf;
  }
}
