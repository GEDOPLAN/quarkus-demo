package de.gedoplan.showcase.service;

import de.gedoplan.showcase.domain.Person;
import de.gedoplan.showcase.persistence.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PersonService {

  @Inject
  PersonRepository personRepository;

  public double getAverageAge() {
    double totalAge = 0;
    int count = 0;
    for (Person person : this.personRepository.findAll()) {
      ++count;
      totalAge += person.getAge();
    }
    return count != 0 ? (totalAge / count) : 0;
  }
}
