package de.gedoplan.showcase.extension.smartrepo;

import java.util.Optional;

public interface PlanetRepository extends SmartRepo<Planet, Long> {
  Optional<Planet> findByName(String name);
}
