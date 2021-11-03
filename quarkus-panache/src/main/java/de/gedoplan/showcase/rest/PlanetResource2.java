package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Planet;
import de.gedoplan.showcase.persistence.PlanetRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "v2/planets", hal = true)
public interface PlanetResource2 extends PanacheRepositoryResource<PlanetRepository, Planet, Long> {
    
}
