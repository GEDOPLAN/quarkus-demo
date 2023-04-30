package de.gedoplan.showcase.restcountries.eu;

import java.util.Collection;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

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
