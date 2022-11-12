package de.gedoplan.showcase.entity;

import java.io.Serializable;

import lombok.*;

// @Entity
// @Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Person implements Serializable {

  private String name;
  private String firstname;

  public Person(String name, String firstname) {
    this.name = name;
    this.firstname = firstname;
  }
}
