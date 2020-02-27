package de.gedoplan.seminar.cdi.demo.basics.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SessionScopedService implements Serializable {
  
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
