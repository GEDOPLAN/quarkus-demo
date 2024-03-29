package de.gedoplan.showcase.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedLongIdEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Planet extends GeneratedLongIdEntity {
  private String name;
  private double mass;

  public void setValues(Planet other) {
    this.name = other.name;
    this.mass = other.mass;
  }
}
