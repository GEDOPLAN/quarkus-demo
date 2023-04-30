package de.gedoplan.seminar.cdi.demo.basics.service;

import jakarta.annotation.PostConstruct;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScopedService {
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
