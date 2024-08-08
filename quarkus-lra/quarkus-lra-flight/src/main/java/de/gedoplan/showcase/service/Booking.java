package de.gedoplan.showcase.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Booking {
  private BookingType type = BookingType.FREE;
  private String lraId;
}
