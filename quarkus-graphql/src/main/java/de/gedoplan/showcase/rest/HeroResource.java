package de.gedoplan.showcase.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
