package de.gedoplan.showcase.service;

import java.util.Date;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventFirer {
    @Inject @Formal
    Event<String> eventSource;

    public void fireEvent() {
        String event = "Event of " + new Date();
        eventSource.fire(event);
        eventSource.fireAsync(event);
    }
}