package de.gedoplan.showcase.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person extends PanacheEntity {

  public String name;
  public String firstname;

  public void setValues(Person other) {
    this.name = other.name;
    this.firstname = other.firstname;
  }

}
