package de.gedoplan.showcase.restcountries.eu;

import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("restcountriesEuMock/v2")
public class RestCountriesEuMockResource {

    private Map<String, Country> countries = Map.of(
        "de", new Country("de", "Germany", "Berlin"),
        "it", new Country("it", "Italy", "Rome"),
        "fr", new Country("fr", "France", "Paris")
    );

    @GET
    @Path("all")
    @Produces("application/json")
    public Collection<Country> getAll() {
        return countries.values();
    }
  
    @GET
    @Path("alpha/{code}")
    @Produces("application/json")
    public Country getByCode(@PathParam("code") String code) {
        Country country = countries.get(code);
        if (country != null)
          return country;

        throw new NotFoundException();
    }
  
}
