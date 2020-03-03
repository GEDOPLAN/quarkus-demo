package de.gedoplan.showcase.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@RequestScoped
public class HelloService {

  @Inject
  Log log;

  void sayHello() {
    this.log.debug("Hello!");
  }

  @PostConstruct
  void postConstruct() {
    this.log.debug("postConstruct");
  }

  @PreDestroy
  void preDestroy() {
    this.log.debug("preDestroy");
  }
}
