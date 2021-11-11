package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import de.gedoplan.showcase.entity.Person;

@ApplicationScoped
public class PersonReceiver {

  @Inject
  Logger logger;

  @Incoming("person")
  public void onReceive(Person person) {
    this.logger.debugf("Received %s", person);
  }
}
