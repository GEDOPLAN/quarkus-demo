package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class PersonReceiver {

  @Inject
  Log log;

  @Incoming("received-person")
  public void onReceive(String personJson) {
    /*
     * TODO AMQP akzeptiert keine serialisierten Objekte als Payload, daher Versand als String (JSON).
     * Besser wäre, wenn dies durch den Channel verkapselt wäre
     */
    Person person = JsonbBuilder.create().fromJson(personJson, Person.class);
    this.log.debug("onReceive(" + person + ")");
  }
}
