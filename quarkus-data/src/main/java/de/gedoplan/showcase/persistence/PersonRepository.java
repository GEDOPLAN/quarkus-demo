package de.gedoplan.showcase.persistence;

import java.util.Optional;
import java.util.stream.Stream;

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
  Stream<Person> findByName(String name);

  // TODO This method is in BasicRepository; it should work without being repeated here
  @Find
  Optional<Person> findById(Integer id);
}
