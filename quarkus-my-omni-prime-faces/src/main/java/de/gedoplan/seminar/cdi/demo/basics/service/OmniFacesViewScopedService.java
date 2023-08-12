package de.gedoplan.seminar.cdi.demo.basics.service;

import jakarta.inject.Inject;

import org.omnifaces.cdi.ViewScoped;

@ViewScoped
public class OmniFacesViewScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public OmniFacesViewScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
