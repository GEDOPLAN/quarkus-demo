package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Person;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;

import lombok.Getter;
import org.apache.commons.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class PersonReceiver {

  @Inject
  Log log;

  private AtomicReference<Person> lastReceived = new AtomicReference<>();

  private BlockingQueue<Person> received = new LinkedBlockingQueue<>();

  @Incoming("received-person")
  public void onReceive(String personJson) {
    /*
     * TODO AMQP akzeptiert keine serialisierten Objekte als Payload, daher Versand als String (JSON).
     * Besser wäre, wenn dies durch den Channel verkapselt wäre
     */
    Person person = JsonbBuilder.create().fromJson(personJson, Person.class);
    this.log.debug("onReceive(" + person + ")");

    try {
      this.received.put(person);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public Person pollReceived(long timeoutMillis) {
    try {
      return this.received.poll(timeoutMillis, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
