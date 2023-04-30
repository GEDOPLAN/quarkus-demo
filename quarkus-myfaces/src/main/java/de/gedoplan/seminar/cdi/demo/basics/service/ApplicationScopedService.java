package de.gedoplan.seminar.cdi.demo.basics.service;

import jakarta.annotation.PostConstruct;
import java.util.Date;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationScopedService {
  private Date instanceCreated;

  public Date getInstanceCreated() {
    return this.instanceCreated;
  }

  @PostConstruct
  public void init() {
    this.instanceCreated = new Date();
  }
}
