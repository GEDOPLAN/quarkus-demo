package de.gedoplan.showcase.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;

import org.jboss.logging.Logger;

@ApplicationScoped
public class PushService3 {

  @Inject
  @Push(channel = "push-3")
  PushContext pushContext;

  @Inject
  Logger logger;

  public void send(String text) {
    var x = this.pushContext.send(text);
    var count = x.size();

    this.logger.debugf("Sent \"%s\" to %d browsers", text, count);
  }
}
