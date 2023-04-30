package de.gedoplan.showcase.rest;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import de.gedoplan.showcase.entity.Hero;

@ApplicationScoped
@Path("heroes")
public class HeroResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hero> getAll() {
        return Hero.listAll();
    }
}
