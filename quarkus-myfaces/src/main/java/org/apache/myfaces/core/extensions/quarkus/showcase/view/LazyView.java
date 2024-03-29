/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.core.extensions.quarkus.showcase.view;

import jakarta.annotation.PostConstruct;
import java.io.Serializable;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("dtLazyView")
@ViewScoped
public class LazyView implements Serializable {

  private LazyDataModel<Car> lazyModel;

  private Car selectedCar;

  @Inject
  CarService service;

  @PostConstruct
  public void init() {
    this.lazyModel = new LazyCarDataModel(this.service.createCars(200));
  }

  public LazyDataModel<Car> getLazyModel() {
    return this.lazyModel;
  }

  public Car getSelectedCar() {
    return this.selectedCar;
  }

  public void setSelectedCar(Car selectedCar) {
    this.selectedCar = selectedCar;
  }

  public void setService(CarService service) {
    this.service = service;
  }

  public void onRowSelect(SelectEvent event) {
    FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
}
