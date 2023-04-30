package de.gedoplan.showcase.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

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
