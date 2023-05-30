package de.gedoplan.seminar.cdi.demo.basics.service;

import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import java.io.Serializable;

@ViewScoped
public class OmniFacesViewScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public OmniFacesViewScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
