package de.gedoplan.showcase.graphql;

import de.gedoplan.showcase.entity.Film;
import de.gedoplan.showcase.entity.Hero;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi
public class FilmResource {

  @Query("allFilms")
  @Description("Get all films")
  public List<Film> getAllFilms() {
    return Film.listAll();
  }

  @Query("film")
  @Description("Get film by id")
  public Film getFilm(@Name("id") Long id) {
    return Film.findById(id);
  }

  public List<Hero> heroes(@Source Film film) {
    return Hero.list("select distinct h from Hero h join h.films f where f=?1", film);
  }
}
