package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.domain.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PersonRepository {

  @Inject
  PersonReactiveRepository reactiveRepository;

  public List<Person> findAll() {
    return this.reactiveRepository
      .listAll()
      .await()
      .indefinitely();
  }

  public void persist(Person person) {
    this.reactiveRepository.persist(person).await().indefinitely();
  }

  public void flush() {
    this.reactiveRepository.flush().await().indefinitely();
  }
}
