package de.gedoplan.showcase.extension.smartrepo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SmartRepoExtensionTest {

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
    assertEquals(2, planets.size());
  }

  @Test
  @Order(3)
  @Transactional
  public void testFindById() throws Exception {
    Optional<Planet> planet = repo.findById(EARTH.getId());
    assertTrue(planet.isPresent());
    assertEquals(EARTH.getId(), planet.get().getId());
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
    assertEquals(EARTH.getId(), planet.get().getId());
  }

  @Test
  @Order(4)
  @Transactional
  public void testFindByUnknownName() throws Exception {
    Optional<Planet> planet = repo.findByName("no-name");
    assertFalse(planet.isPresent());
  }

}