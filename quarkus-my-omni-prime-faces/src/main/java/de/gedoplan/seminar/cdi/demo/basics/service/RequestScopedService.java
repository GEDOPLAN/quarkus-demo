package de.gedoplan.seminar.cdi.demo.basics.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public RequestScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
