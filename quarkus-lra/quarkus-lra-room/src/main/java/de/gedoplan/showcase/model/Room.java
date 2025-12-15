package de.gedoplan.showcase.model;

import de.gedoplan.baselibs.persistence.entity.StringIdEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room extends StringIdEntity {
  private String name;
  private String location;
  private int noOfSeats;

  public Room(String id, String name, String location, int noOfSeats) {
    super(id);
    this.name = name;
    this.location = location;
    this.noOfSeats = noOfSeats;
  }
}
