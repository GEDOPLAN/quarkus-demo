package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@ApplicationScoped
public class FaultTolerantService {

  @Inject
  UnreliableService unreliableService;

  @Inject
  Log log;

  public int doSomething() {
    return this.unreliableService.doSomething(25, 25);
  }

  @Retry(maxRetries = 4)
  public int doSomethingWithRetry() {
    return this.unreliableService.doSomething(0, 75);
  }

  @Timeout(1000)
  public int doSomethingWithTimeout() {
    return this.unreliableService.doSomething(50, 0);
  }

  @CircuitBreaker(failureRatio = 0.25, requestVolumeThreshold = 10)
  public int doSomethingWithCircuitBreaker() {
    return this.unreliableService.doSomething(0, 50);
  }

  @Fallback(fallbackMethod = "return42")
  public int doSomethingWithFallback() {
    return this.unreliableService.doSomething(0, 50);
  }

  @SuppressWarnings("unused")
  private int return42() {
    this.log.debug("Using default value 42");
    return 42;
  }
}
