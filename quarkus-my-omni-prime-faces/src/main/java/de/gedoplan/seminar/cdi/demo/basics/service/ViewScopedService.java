package de.gedoplan.seminar.cdi.demo.basics.service;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;

@ViewScoped
public class ViewScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public ViewScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
