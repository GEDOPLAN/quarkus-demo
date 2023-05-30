package de.gedoplan.seminar.cdi.demo.basics.service;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class SessionScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public SessionScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
