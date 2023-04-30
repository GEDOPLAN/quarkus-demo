package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Person;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class InitPersonDemoDataService {
  @Inject
  EntityManager entityManager;

  private static Log log = LogFactory.getLog(InitPersonDemoDataService.class);

  /**
   * Create test/demo data.
   * Attn: Interceptors may not be called, if method is private!
   *
   * @param event Application scope initialization event
   */
  @Transactional
  void createDemoData(@Observes StartupEvent event) {
    try {
      if (this.entityManager.createQuery("select count(x) from Person x", Long.class).getSingleResult() == 0) {
        this.entityManager.persist(new Person("Duck", "Dagobert"));
        this.entityManager.persist(new Person("Duck", "Donald"));
      }
    } catch (Exception e) {
      log.warn("Cannot create demo data", e);
    }

  }

}
