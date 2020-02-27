package de.gedoplan.seminar.cdi.demo.basics.service;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

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
