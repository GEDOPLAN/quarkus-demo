package de.gedoplan.showcase.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.domain.User;

import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserRepository extends SingleIdEntityRepository<String, User> {

  @Inject
  Logger logger;

  @Transactional
  void fillInDemoData(@Observes StartupEvent startupEvent) {
    if (countAll()==0) {
      persist(new User("hugo", "hugo_123", "Hugo Testuser", "demoRole", "otherRole", "guest"));
      persist(new User("otto", "otto_123", "Otto Testuser", "demoRole", "guest"));
      persist(new User("willi", "willi_123", "Willi Testuser", "guest"));
    }

    logger.debug("Users in DB:");
    findAll().forEach(u -> logger.debugf("  %s %s", u, u.getRoles().stream().collect(Collectors.joining(",", "[", "]"))));
  }
}
