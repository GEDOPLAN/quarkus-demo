package de.gedoplan.showcase.service;

import java.time.temporal.ChronoUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import de.gedoplan.showcase.entity.Person;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
public class PersonReceiver {

  @Inject
  Logger logger;

  @Getter
  @Setter
  private int simulatedFailureNumber = 0;

  @Incoming("person")
  @Retry(delay = 1, delayUnit = ChronoUnit.SECONDS, maxRetries = 3)
  public void onReceive(ConsumerRecord<String, Person> record) {
    boolean simulateFailure = simulatedFailureNumber != 0;
    if (simulatedFailureNumber > 0)
      --simulatedFailureNumber;
    if (simulateFailure) {
      logger.debugf("Simulated failure (%s)", simulatedFailureNumber >= 0 ? "remaining: "+simulatedFailureNumber : "for ever");
      throw new RuntimeException("Simulated failure");
    }

    Person person = record.value();
    int partition = record.partition();
    long offset = record.offset();
    this.logger.debugf("Received %s (partition %d, offset %d)", person, partition, offset);
  }
}
