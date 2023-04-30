package de.gedoplan.showcase.service;

import java.time.temporal.ChronoUnit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import de.gedoplan.showcase.entity.Person;

@ApplicationScoped
public class DLQReceiver {

    @Inject
    Logger logger;
  
    @Incoming("DLQ")
    public void onReceive(ConsumerRecord<String, String> record) {
      logger.debugf("Dead letter: %s", record.value());
    }
      
}
