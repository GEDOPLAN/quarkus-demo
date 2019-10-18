package de.gedoplan.showcase.restcountries.eu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.net.URI;
import java.util.Collection;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CountryApiTest {

  CountryApi client;

  @BeforeEach
  void before() {
    try {
      this.client = RestClientBuilder.newBuilder()
          .baseUri(new URI("https://restcountries.eu/rest"))
          .build(CountryApi.class);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Test
  public void test_01_getByCode() {
    Country country = this.client.getByCode("de");
    assertEquals("Germany", country.getName());
  }

  @Test
  public void test_02_getAll() {
    Collection<Country> countries = this.client.getAll();
    assertNotEquals(0, countries.size());
  }

}
