package de.gedoplan.seminar.cdi.demo.basics.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@ViewScoped
public class ViewScopedService implements Serializable {

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
