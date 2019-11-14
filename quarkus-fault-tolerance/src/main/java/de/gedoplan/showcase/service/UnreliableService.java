package de.gedoplan.showcase.service;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@ApplicationScoped
public class UnreliableService {

  private Random random = new Random();

  @Inject
  Log log;

  public int doSomething(int slowPercent, int errorPercent) {
    int i = this.random.nextInt(100);

    if (i < slowPercent) {
      this.log.debug("doSomething: Slow operation returning " + i);
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
      }
      return i;
    }

    if (i < slowPercent + errorPercent) {
      this.log.debug("doSomething: Error");
      throw new RuntimeException("Error");
    }

    this.log.debug("doSomething: Normal operation returning " + i);
    return i;
  }
}
