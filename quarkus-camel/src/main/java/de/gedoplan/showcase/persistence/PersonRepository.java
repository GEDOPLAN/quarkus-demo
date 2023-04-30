package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.entity.Person;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.POST;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@Named
public class PersonRepository {
  private ConcurrentHashMap<Integer, Person> persons = new ConcurrentHashMap<>();

  public Collection<Person> findAll() {
    return persons.values();
  }

  public Person findById(int id) {
    return persons.get(id);
  }

  public void save(Person person) {
    persons.put(person.getId(), person);
  }

  @PostConstruct
  void init() {
    save(new Person(1, "Duck", "Dagobert"));
    save(new Person(2, "Duck", "Donald"));
  }
}
