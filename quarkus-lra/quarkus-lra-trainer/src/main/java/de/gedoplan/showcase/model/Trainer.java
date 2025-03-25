package de.gedoplan.showcase.model;

import de.gedoplan.baselibs.persistence.entity.StringIdEntity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Trainer extends StringIdEntity {
  private String name;

  @ElementCollection
  @CollectionTable(name = "TRAINER_COURSES")
  private Set<String> courses;

  public Trainer(String id, String name, String ... courses) {
    super(id);
    this.name = name;
    this.courses = Set.of(courses);
  }
}
