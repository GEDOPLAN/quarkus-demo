package de.gedoplan.showcase.persistence;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.gedoplan.showcase.domain.Person;

@ApplicationScoped
public class PersonRepository {
  @Inject
  EntityManager entityManager;

  public void persist(Person person) {
    this.entityManager.persist(person);
  }

  public Person merge(Person person) {
    return this.entityManager.merge(person);
  }

  public void removeById(Integer id) {
    Person person = findById(id);
    if (person != null)
      this.entityManager.remove(person);
  }
  public Person findById(Integer id) {
    return this.entityManager.find(Person.class, id);
  }

  public List<Person> findAll() {
    return this.entityManager
      .createQuery("select x from Person x", Person.class)
      .getResultList();
  }
}
