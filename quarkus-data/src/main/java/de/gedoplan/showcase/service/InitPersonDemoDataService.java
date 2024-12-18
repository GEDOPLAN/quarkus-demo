package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

@ApplicationScoped
public class InitPersonDemoDataService {
  @Inject
  PersonRepository personRepository;

  @Inject
  Logger log;

  /**
   * Create test/demo data.
   * Attn: Interceptors may not be called, if method is private!
   *
   * @param event Application scope initialization event
   */
  @Transactional
  void createDemoData(@Observes StartupEvent event) {
    try {
      if (this.personRepository.count() == 0) {
        this.personRepository.insert(new Person("Duck", "Dagobert"));
        this.personRepository.insert(new Person("Duck", "Donald"));
      }
    } catch (Exception e) {
      log.warn("Cannot create demo data", e);
    }

  }

}
