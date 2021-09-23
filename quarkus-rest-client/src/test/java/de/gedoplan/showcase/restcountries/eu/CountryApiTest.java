package de.gedoplan.showcase.restcountries.eu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.net.URI;
import java.util.Collection;

import com.google.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CountryApiTest {

  @Inject
  @ConfigProperty(name = "de.gedoplan.showcase.restcountries.eu.CountryApi/mp-rest/url")
  URI restCountriesEuUri;

  CountryApi client;

  @BeforeEach
  void before() {
    try {
      this.client = RestClientBuilder.newBuilder()
          .baseUri(restCountriesEuUri)
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
