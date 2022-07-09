package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@ApplicationScoped
public class EventObserver {
    @Inject 
    Log log;

    void logSyncEvent(@Observes Object event) {
        log.debug("Sync event: " + event);
    }

    void logAsyncEvent(@ObservesAsync Object event) {
        log.debug("Async event: " + event);
    }
}
