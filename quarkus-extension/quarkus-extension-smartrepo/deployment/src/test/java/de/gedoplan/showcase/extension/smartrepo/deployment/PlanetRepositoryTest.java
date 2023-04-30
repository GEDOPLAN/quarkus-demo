package de.gedoplan.showcase.extension.smartrepo.deployment;

import io.quarkus.test.QuarkusUnitTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanetRepositoryTest {

  @RegisterExtension
  static final QuarkusUnitTest TEST = new QuarkusUnitTest()
    .setArchiveProducer(
      () -> ShrinkWrap.create(JavaArchive.class)
        .addClasses(Planet.class, PlanetRepository.class))
    .withConfigurationResource("application.properties");

  @Inject
  PlanetRepository repo;

  private static final Planet EARTH = new Planet("Earth", 5.9722e24);
  private static final Planet JUPITER = new Planet("Jupiter", 1.899e27);

  @Test
  @Order(1)
  @Transactional
  public void testInsert() throws Exception {
    repo.persist(EARTH);
    assertNotNull(EARTH.getId());

    repo.persist(JUPITER);
    assertNotNull(JUPITER.getId());
  }

  @Test
  @Order(2)
  @Transactional
  public void testFindAll() throws Exception {
    List<Planet> planets = repo.findAll();
    assertThat(planets.size()).isEqualTo(2);
  }

  @Test
  @Order(3)
  @Transactional
  public void testFindById() throws Exception {
    Optional<Planet> planet = repo.findById(EARTH.getId());
    assertTrue(planet.isPresent());
    assertThat(planet.get().getId()).isEqualTo(EARTH.getId());
  }

  @Test
  @Order(3)
  @Transactional
  public void testFindByUnknownId() throws Exception {
    Optional<Planet> planet = repo.findById(-1234L);
    assertFalse(planet.isPresent());
  }

  @Test
  @Order(4)
  @Transactional
  public void testFindByName() throws Exception {
    Optional<Planet> planet = repo.findByName(EARTH.getName());
    assertTrue(planet.isPresent());
    assertThat(planet.get().getId()).isEqualTo(EARTH.getId());
  }

  @Test
  @Order(4)
  @Transactional
  public void testFindByUnknownName() throws Exception {
    Optional<Planet> planet = repo.findByName("no-name");
    assertFalse(planet.isPresent());
  }

}
