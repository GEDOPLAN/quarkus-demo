package de.gedoplan.seminar.cdi.demo.basics.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class DependentScopedService {
  private static int nextNumber = 1;

  private int instanceNumber;

  public int getInstanceNumber() {
    return this.instanceNumber;
  }

  @PostConstruct
  public void init() {
    this.instanceNumber = nextNumber;
    ++nextNumber;
  }

}
