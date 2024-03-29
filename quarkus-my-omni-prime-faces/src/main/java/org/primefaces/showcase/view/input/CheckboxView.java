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
package org.primefaces.showcase.view.input;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.inject.Named;

import org.primefaces.event.UnselectEvent;

@Named
@RequestScoped
public class CheckboxView {

  private String[] selectedOptions;
  private String[] selectedOptions2;
  private String[] selectedCities;
  private String[] selectedCities2;
  private List<String> cities;
  private List<SelectItem> countries;
  private String[] selectedCountries;

  @PostConstruct
  public void init() {
    this.cities = new ArrayList<>();
    this.cities.add("Miami");
    this.cities.add("London");
    this.cities.add("Paris");
    this.cities.add("Istanbul");
    this.cities.add("Berlin");
    this.cities.add("Barcelona");
    this.cities.add("Rome");
    this.cities.add("Brasilia");
    this.cities.add("Amsterdam");

    this.countries = new ArrayList<>();
    SelectItemGroup europeCountries = new SelectItemGroup("European Countries");
    europeCountries.setSelectItems(new SelectItem[] {
      new SelectItem("Germany", "Germany"),
      new SelectItem("Turkey", "Turkey"),
      new SelectItem("Spain", "Spain")
    });

    SelectItemGroup americaCountries = new SelectItemGroup("American Countries");
    americaCountries.setSelectItems(new SelectItem[] {
      new SelectItem("United States", "United States"),
      new SelectItem("Brazil", "Brazil"),
      new SelectItem("Mexico", "Mexico")
    });

    this.countries.add(europeCountries);
    this.countries.add(americaCountries);
  }

  public String[] getSelectedOptions() {
    return this.selectedOptions;
  }

  public void setSelectedOptions(String[] selectedOptions) {
    this.selectedOptions = selectedOptions;
  }

  public String[] getSelectedOptions2() {
    return this.selectedOptions2;
  }

  public void setSelectedOptions2(String[] selectedOptions2) {
    this.selectedOptions2 = selectedOptions2;
  }

  public String[] getSelectedCities() {
    return this.selectedCities;
  }

  public void setSelectedCities(String[] selectedCities) {
    this.selectedCities = selectedCities;
  }

  public String[] getSelectedCities2() {
    return this.selectedCities2;
  }

  public void setSelectedCities2(String[] selectedCities2) {
    this.selectedCities2 = selectedCities2;
  }

  public List<String> getCities() {
    return this.cities;
  }

  public void setCities(List<String> cities) {
    this.cities = cities;
  }

  public List<SelectItem> getCountries() {
    return this.countries;
  }

  public void setCountries(List<SelectItem> countries) {
    this.countries = countries;
  }

  public String[] getSelectedCountries() {
    return this.selectedCountries;
  }

  public void setSelectedCountries(String[] selectedCountries) {
    this.selectedCountries = selectedCountries;
  }

  public void onItemUnselect(UnselectEvent event) {
    FacesContext context = FacesContext.getCurrentInstance();

    FacesMessage msg = new FacesMessage();
    msg.setSummary("Item unselected: " + event.getObject().toString());
    msg.setSeverity(FacesMessage.SEVERITY_INFO);

    context.addMessage(null, msg);
  }
}
