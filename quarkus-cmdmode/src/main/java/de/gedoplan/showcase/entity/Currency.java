package de.gedoplan.showcase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class Currency  {

  private String id;
  private BigDecimal euroValue;

}