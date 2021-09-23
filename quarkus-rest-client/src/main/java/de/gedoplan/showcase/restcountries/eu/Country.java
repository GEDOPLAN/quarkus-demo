package de.gedoplan.showcase.restcountries.eu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Country {
  private String alpha2Code;
  private String name;
  private String capital;
}
