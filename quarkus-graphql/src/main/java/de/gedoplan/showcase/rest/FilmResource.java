package de.gedoplan.showcase.rest;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.gedoplan.showcase.entity.Film;

@ApplicationScoped
@Path("films")
public class FilmResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Film> getAll() {
        return Film.listAll();
    }
}
