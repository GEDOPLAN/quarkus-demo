package de.gedoplan.showcase.rest;

import java.util.Collection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
