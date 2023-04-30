package de.gedoplan.showcase.persistence;

import jakarta.enterprise.context.ApplicationScoped;

import de.gedoplan.showcase.entity.Planet;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PlanetRepository implements PanacheRepository<Planet> {

    public Planet findByName(String name) {
        return find("name", name).firstResult();
    }
    
}
