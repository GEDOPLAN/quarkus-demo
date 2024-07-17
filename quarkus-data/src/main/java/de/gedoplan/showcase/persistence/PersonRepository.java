package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.entity.Person;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;


@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
  // TODO This should work without annotation
  @Query("select count(x) from Person x")
  long count();

  // TODO This should work without annotation
  @Find
  Person findByName(String name);
}
