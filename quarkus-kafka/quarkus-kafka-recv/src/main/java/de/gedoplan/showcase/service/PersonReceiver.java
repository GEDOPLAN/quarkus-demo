package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import de.gedoplan.showcase.entity.Person;

@ApplicationScoped
public class PersonReceiver {

  @Inject
  Logger logger;

  @Incoming("person")
  // public void onReceive(Person person) {
  //   this.logger.debugf("Received %s", person);
  // }
  public void onReceive(ConsumerRecord<String, Person> record) {
    Person person = record.value();
    int partition = record.partition();
    long offset = record.offset();
    this.logger.debugf("Received %s (partition %d, offset %d)", person, partition, offset);
  }
}
