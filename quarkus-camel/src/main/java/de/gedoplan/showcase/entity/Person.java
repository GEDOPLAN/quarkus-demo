package de.gedoplan.showcase.entity;

import lombok.*;

import java.io.Serializable;

// @Entity
// @Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class Person implements Serializable {

  // @Id
  private int id;
  private String name;
  private String firstname;

  public Person(int id, String name, String firstname) {
    this.id = id;
    this.name = name;
    this.firstname = firstname;
  }
}
