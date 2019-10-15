package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class InitPersonDemoDataService {
  @Inject
  PersonRepository personRepository;

  @Inject
  Log log;

  /**
   * Create test/demo data.
   * Attn: Interceptors may not be called, if method is private!
   *
   * @param event
   *          Application scope initialization event
   */
  @Transactional
  void createDemoData(@Observes StartupEvent event) {
    try {
      if (personRepository.countAll() == 0) {
        log.debug("Create demo data");

        personRepository.persist(new Person("Duck", "Dagobert"));
        personRepository.persist(new Person("Duck", "Donald"));
      }
    } catch (Exception e) {
      log.warn("Cannot create demo data", e);
    }

  }
}
