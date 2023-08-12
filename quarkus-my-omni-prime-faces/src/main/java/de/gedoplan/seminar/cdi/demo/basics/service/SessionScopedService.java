package de.gedoplan.seminar.cdi.demo.basics.service;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

@SessionScoped
public class SessionScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public SessionScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
