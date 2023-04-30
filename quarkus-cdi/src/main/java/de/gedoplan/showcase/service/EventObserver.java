package de.gedoplan.showcase.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

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
