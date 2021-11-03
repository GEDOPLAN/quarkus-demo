package de.gedoplan.showcase.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gedoplan.showcase.entity.Film;
import de.gedoplan.showcase.entity.Hero;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class InitDemoDataService {
  @Inject
  EntityManager entityManager;

  private static Log log = LogFactory.getLog(InitDemoDataService.class);

  /**
   * Create test/demo data.
   * Attn: Interceptors may not be called, if method is private!
   *
   * @param event Application scope initialization event
   */
  @Transactional
  void createDemoData(@Observes StartupEvent event) {
    try {
      if (Film.count() == 0) {
        List<Film> films = List.of(
          Film.builder()
            .title("Star Wars - A New Hope")
            .director("George Lucas")
            .releaseDate(LocalDate.of(1977, Month.MAY, 25))
            .build(),
          
          Film.builder()
            .title("Star Wars - The Empire Strikes Back")
            .director("George Lucas")
            .releaseDate(LocalDate.of(1980, Month.MAY, 21))
            .build(),
            
          Film.builder()
            .title("Star Wars - Return Of The Jedi")
            .director("George Lucas")
            .releaseDate(LocalDate.of(1983, Month.MAY, 25))
            .build());

        films.forEach(f -> f.persist());

        List<Hero> heros = List.of(
          Hero.builder()
            .name("Luke")
            .surname("Skywalker")
            .height(1.7)
            .mass(73)
            .lightSwordColor("green")
            .darkSide(false)
            .films(films)
            .build(),            

          Hero.builder()
            .name("Leia")
            .surname("Organa")
            .height(1.5)
            .mass(51)
            .darkSide(false)
            .films(films)
            .build(),            

          Hero.builder()
            .name("Darth")
            .surname("Vader")
            .height(1.9)
            .mass(89)
            .lightSwordColor("red")
            .darkSide(true)
            .films(films)
            .build()            
        );

        heros.forEach(h -> h.persist());
      }
    } catch (Exception e) {
      log.warn("Cannot create demo data", e);
    }

  }

}
