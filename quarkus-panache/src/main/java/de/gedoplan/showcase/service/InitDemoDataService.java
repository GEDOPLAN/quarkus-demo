package de.gedoplan.showcase.service;

import java.util.stream.IntStream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.entity.Planet;
import de.gedoplan.showcase.persistence.PlanetRepository;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class InitDemoDataService {

  @Inject
  PlanetRepository planetRepository;

  /**
   * Create test/demo data.
   * Attn: Interceptors may not be called, if method is private!
   *
   * @param event Application scope initialization event
   */
  @Transactional
  void createDemoData(@Observes StartupEvent event) {
    if (Person.count() == 0) {
      new Person("Duck", "Dagobert").persist();
      new Person("Duck", "Donald").persist();
    } 

    if (this.planetRepository.count() == 0) {
      this.planetRepository.persist(new Planet("earth", 1e19));
      this.planetRepository.persist(new Planet("jupiter", 1e24));

      IntStream.rangeClosed(1, 1000).forEach(i -> this.planetRepository.persist(new Planet("X-" + i, 5e3)));
    }
  }

}
