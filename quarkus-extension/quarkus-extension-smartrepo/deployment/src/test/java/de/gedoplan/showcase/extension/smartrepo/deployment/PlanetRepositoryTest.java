package de.gedoplan.showcase.extension.smartrepo.deployment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.QuarkusUnitTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

  private Planet earth;
  private Planet jupiter;

  @Test
  @Order(1)
  @Transactional
  public void testInsert() throws Exception {
    earth = repo.save(new Planet("Earth", 5.9722e24));
    assertNotNull(earth.getId());

    jupiter = repo.save(new Planet("Jupiter", 1.899e27));
    assertNotNull(jupiter.getId());
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
    Optional<Planet> planet = repo.findById(earth.getId());
    assertTrue(planet.isPresent());
    assertThat(planet.get().getId()).isEqualTo(earth.getId());
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
    Optional<Planet> planet = repo.findByName(earth.getName());
    assertTrue(planet.isPresent());
    assertThat(planet.get().getId()).isEqualTo(earth.getId());
  }

  @Test
  @Order(4)
  @Transactional
  public void testFindByUnknownName() throws Exception {
    Optional<Planet> planet = repo.findByName("no-name");
    assertFalse(planet.isPresent());
  }

}
