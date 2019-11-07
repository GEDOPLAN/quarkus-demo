package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class PersonReceiver {

  @Inject
  Log log;

  @Incoming("received-person")
  public void onReceive(Person person) {
    this.log.debug("onReceive(" + person + ")");
  }
}
