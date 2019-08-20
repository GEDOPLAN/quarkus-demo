package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@Dependent
public class LifecycleLoggerService {

  // TODO Quarkus verweigert statische Producer-Methoden!
  // @Inject
  Log log = LogFactory.getLog(LifecycleLoggerService.class);

  void logStartup(@Observes StartupEvent event) {
    this.log.info("Startup");
  }

  void logShutdown(@Observes ShutdownEvent event) {
    this.log.info("Shutdown");
  }

  void logApplicationScopeInitialized(@Observes @Initialized(ApplicationScoped.class) Object event) {
    this.log.info("ApplicationScope initialized");
  }

  void logApplicationScopeDestroyed(@Observes @Destroyed(ApplicationScoped.class) Object event) {
    this.log.info("ApplicationScope destroyed");
  }

  void logRequestScopeInitialized(@Observes @Initialized(RequestScoped.class) Object event) {
    this.log.info("RequestScope initialized");
  }

  void logRequestScopeDestroyed(@Observes @Destroyed(RequestScoped.class) Object event) {
    this.log.info("RequestScope destroyed");
  }

}
