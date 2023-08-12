package de.gedoplan.seminar.cdi.demo.basics.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class DependentScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public DependentScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
