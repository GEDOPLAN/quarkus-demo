package de.gedoplan.showcase.extension.smartrepo.deployment;

import de.gedoplan.showcase.extension.smartrepo.SmartRepo;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends SmartRepo<Planet, Long> {
  Planet save(Planet item);
  List<Planet> findAll();

  Optional<Planet> findById(Long id);

  Optional<Planet> findByName(String name);
}
