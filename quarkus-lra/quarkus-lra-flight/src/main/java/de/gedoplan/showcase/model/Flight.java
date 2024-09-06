package de.gedoplan.showcase.model;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
public class Flight extends SingleIdEntity<Character> {

  @Id
  @Setter(AccessLevel.NONE)
  private Character id;

  @Enumerated(EnumType.STRING)
  private BookingType bookingType;
  private String lraId;

  public Flight(Character id) {
    this.id = id;
    this.bookingType=BookingType.FREE;
  }
}
