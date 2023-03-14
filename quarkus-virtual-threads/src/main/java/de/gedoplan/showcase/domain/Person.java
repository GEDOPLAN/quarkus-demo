package de.gedoplan.showcase.domain;

import java.time.LocalDate;
import java.time.Period;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private LocalDate birthDay;

  public Person(String name, LocalDate birthDay) {
    this.name = name;
    this.birthDay = birthDay;
  }

  @JsonbTransient
  public int getAge() {
    return Period
      .between(this.birthDay, LocalDate.now())
      .getYears();
  }

}