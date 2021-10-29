package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
    }
  }

}
