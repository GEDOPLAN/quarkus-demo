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
package org.primefaces.showcase.view.ajax;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class BasicView implements Serializable {

  private String text1;

  private String text2;

  private String text3;

  private String text4;

  private String text5;

  private int number;

  private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();

  private String country;

  private String city;

  private Map<String, String> countries;

  private Map<String, String> cities;

  @PostConstruct
  public void init() {
      this.countries = new HashMap<String, String>();
      this.countries.put("USA", "USA");
      this.countries.put("Germany", "Germany");
      this.countries.put("Brazil", "Brazil");

    Map<String, String> map = new HashMap<String, String>();
    map.put("New York", "New York");
    map.put("San Francisco", "San Francisco");
    map.put("Denver", "Denver");
      this.data.put("USA", map);

    map = new HashMap<String, String>();
    map.put("Berlin", "Berlin");
    map.put("Munich", "Munich");
    map.put("Frankfurt", "Frankfurt");
      this.data.put("Germany", map);

    map = new HashMap<String, String>();
    map.put("Sao Paulo", "Sao Paulo");
    map.put("Rio de Janerio", "Rio de Janerio");
    map.put("Salvador", "Salvador");
      this.data.put("Brazil", map);
  }

  public void increment() {
      this.number++;
  }

  public void handleKeyEvent() {
      this.text5 = this.text5.toUpperCase();
  }

  public String getText1() {
    return this.text1;
  }

  public void setText1(String text1) {
    this.text1 = text1;
  }

  public String getText2() {
    return this.text2;
  }

  public void setText2(String text2) {
    this.text2 = text2;
  }

  public String getText3() {
    return this.text3;
  }

  public void setText3(String text3) {
    this.text3 = text3;
  }

  public String getText4() {
    return this.text4;
  }

  public void setText4(String text4) {
    this.text4 = text4;
  }

  public String getText5() {
    return this.text5;
  }

  public void setText5(String text5) {
    this.text5 = text5;
  }

  public int getNumber() {
    return this.number;
  }

  public Map<String, Map<String, String>> getData() {
    return this.data;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Map<String, String> getCountries() {
    return this.countries;
  }

  public Map<String, String> getCities() {
    return this.cities;
  }

  public void onCountryChange() {
      if (this.country != null && !this.country.equals("")) {
          this.cities = this.data.get(this.country);
      } else {
          this.cities = new HashMap<String, String>();
      }
  }

  public void displayLocation() {
    FacesMessage msg;
      if (this.city != null && this.country != null) {
          msg = new FacesMessage("Selected", this.city + " of " + this.country);
      } else {
          msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");
      }

    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
}
