package de.gedoplan.seminar.cdi.demo.basics.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class RequestScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public RequestScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
