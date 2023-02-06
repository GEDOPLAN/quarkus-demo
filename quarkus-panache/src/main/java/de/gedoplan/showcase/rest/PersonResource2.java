package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.entity.Planet;
import de.gedoplan.showcase.persistence.PlanetRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "v2/persons", hal = true)
public interface PersonResource2 extends PanacheEntityResource<Person, Long> {
    
}
