package de.gedoplan.showcase.entity;

import io.quarkus.hibernate.panache.PanacheEntity;
import io.quarkus.hibernate.panache.PanacheRepository;
import jakarta.data.repository.Find;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.processing.HQL;

@Entity
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person extends PanacheEntity {

  private String name;
  private String firstname;

  public Person(String name, String firstname) {
    this.name = name;
    this.firstname = firstname;
  }

  public interface Repository extends PanacheRepository<Person> {
    @Find
    Person findByName(String name);

    @HQL("delete from Person where name = :name")
    long deleteByName(String name);
  }
}
