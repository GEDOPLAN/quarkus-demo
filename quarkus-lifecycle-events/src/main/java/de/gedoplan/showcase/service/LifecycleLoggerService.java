package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
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

}
