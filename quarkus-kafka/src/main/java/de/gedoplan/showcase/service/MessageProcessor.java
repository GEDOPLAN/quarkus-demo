package de.gedoplan.showcase.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@Dependent
public class MessageProcessor {

  @Inject
  Log log;

  @Incoming("demo-channel-1")
  @Outgoing("demo-channel-2")
  String process(String text) {
    this.log.debug("Processing " + text);

    return text.toUpperCase();
  }
}
