package de.gedoplan.showcase.restcountries.eu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Country {
  private String alpha2Code;
  private String name;
  private String capital;
}
