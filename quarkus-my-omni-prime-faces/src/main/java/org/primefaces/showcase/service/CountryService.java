/*
 * The MIT License
 *
 * Copyright (c) 2009-2021 PrimeTek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primefaces.showcase.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import org.primefaces.showcase.domain.Country;

@Named
@ApplicationScoped
public class CountryService {

  private List<Country> countries;
  private Map<Integer, Country> countriesAsMap;
  private List<Country> locales;
  private Map<Integer, Country> localesAsMap;

  @PostConstruct
  public void init() {
      this.countries = new ArrayList<>();
      this.locales = new ArrayList<>();

    String[] isoCodes = Locale.getISOCountries();

    for (int i = 0; i < isoCodes.length; i++) {
      Locale locale = new Locale("", isoCodes[i]);
        this.countries.add(new Country(i, locale));
    }

    Collections.sort(this.countries, (Country c1, Country c2) -> c1.getName().compareTo(c2.getName()));

    int i = 0;
      this.locales.add(new Country(i++, Locale.US));
      this.locales.add(new Country(i++, Locale.FRANCE));
      this.locales.add(new Country(i++, Locale.GERMANY));
      this.locales.add(new Country(i++, Locale.ITALY));
      this.locales.add(new Country(i++, new Locale("es", "ES")));
      this.locales.add(new Country(i++, new Locale("ca", "ES")));
      this.locales.add(new Country(i++, new Locale("nl", "NL")));
      this.locales.add(new Country(i++, new Locale("pt", "BR")));
      this.locales.add(new Country(i++, new Locale("pt", "PT")));
      this.locales.add(new Country(i++, new Locale("ar", "SA"), true));
      this.locales.add(new Country(i++, new Locale("cs", "CZ")));
      this.locales.add(new Country(i++, new Locale("el", "GR")));
      this.locales.add(new Country(i++, new Locale("fa", "IR"), true));
      this.locales.add(new Country(i++, new Locale("hr", "HR")));
      this.locales.add(new Country(i++, new Locale("hu", "HU")));
      this.locales.add(new Country(i++, new Locale("iw", "IL"), true));
      this.locales.add(new Country(i++, new Locale("ka", "GE")));
      this.locales.add(new Country(i++, new Locale("lv", "LV")));
      this.locales.add(new Country(i++, new Locale("no", "NO")));
      this.locales.add(new Country(i++, new Locale("pl", "PL")));
      this.locales.add(new Country(i++, new Locale("ro", "RO")));
      this.locales.add(new Country(i++, new Locale("ru", "RU")));
      this.locales.add(new Country(i++, new Locale("sk", "SK")));
      this.locales.add(new Country(i++, new Locale("sl", "SI")));
      this.locales.add(new Country(i++, new Locale("sr", "RS")));
      this.locales.add(new Country(i++, new Locale("sv", "SE")));
      this.locales.add(new Country(i++, new Locale("tr", "TR")));
      this.locales.add(new Country(i++, new Locale("uk", "UA")));
      this.locales.add(new Country(i++, new Locale("vi", "VN")));
      this.locales.add(new Country(i++, Locale.SIMPLIFIED_CHINESE));
      this.locales.add(new Country(i++, Locale.TRADITIONAL_CHINESE));
  }

  public List<Country> getCountries() {
    return new ArrayList<>(this.countries);
  }

  public Map<Integer, Country> getCountriesAsMap() {
    if (this.countriesAsMap == null) {
        this.countriesAsMap = getCountries().stream().collect(Collectors.toMap(Country::getId, country -> country));
    }
    return this.countriesAsMap;
  }

  public List<Country> getLocales() {
    return new ArrayList<>(this.locales);
  }

  public Map<Integer, Country> getLocalesAsMap() {
    if (this.localesAsMap == null) {
        this.localesAsMap = getLocales().stream().collect(Collectors.toMap(Country::getId, country -> country));
    }
    return this.localesAsMap;
  }
}
