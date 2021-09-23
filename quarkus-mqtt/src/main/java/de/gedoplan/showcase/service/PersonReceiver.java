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
  public void onReceive(byte[] personBytes) {
    /*
     * TODO 
     * MQTT akzeptiert keine serialisierten Objekte als Payload, daher Empfang als byte[] (JSON).
     * Besser wäre, wenn dies durch den Channel verkapselt wäre
     */
    String personJson = new String(personBytes);
    Person person = JsonbBuilder.create().fromJson(personJson, Person.class);
    this.log.debug("onReceive(" + person + ")");
  }
}
