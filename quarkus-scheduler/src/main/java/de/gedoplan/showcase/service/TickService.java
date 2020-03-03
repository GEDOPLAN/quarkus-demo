package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class TickService {

  @Inject
  Log log;

  @Inject
  HelloService helloService;

  @Scheduled(every = "10s")
  void every10s() {
    this.log.debug("every10s");
    this.helloService.sayHello();
  }

  @Scheduled(cron = "3/5 * * * * ?")
  void onSecond3_8_13_18_23_28_33_38_43_48_53_58() {
    this.log.debug("onSecond3_8_13_18_23_28_33_38_43_48_53_58");
    this.helloService.sayHello();
  }
}
