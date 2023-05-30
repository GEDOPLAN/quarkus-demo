package de.gedoplan.seminar.cdi.demo.basics.service;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DependentScopedService extends ScopedService {

  private static int lastNumber = 0;

  @Inject
  public DependentScopedService() {
    this.instanceNumber = ++lastNumber;
  }
}
