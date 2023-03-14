package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.domain.Person;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonReactiveRepository implements PanacheRepository<Person> {
}
