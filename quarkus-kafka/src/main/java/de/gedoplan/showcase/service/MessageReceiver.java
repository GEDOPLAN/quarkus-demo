package de.gedoplan.showcase.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Dependent
public class MessageReceiver {

  @Inject
  Log log;

  @Incoming("demo-channel-2")
  void receive(String text) {
    this.log.debug("Received " + text);
  }
}
