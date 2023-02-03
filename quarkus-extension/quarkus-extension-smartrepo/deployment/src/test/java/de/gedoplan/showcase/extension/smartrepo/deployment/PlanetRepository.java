package de.gedoplan.showcase.extension.smartrepo.deployment;

import de.gedoplan.showcase.extension.smartrepo.SmartRepo;

import java.util.Optional;

public interface PlanetRepository extends SmartRepo<Planet, Long> {
  Optional<Planet> findByName(String name);
}
