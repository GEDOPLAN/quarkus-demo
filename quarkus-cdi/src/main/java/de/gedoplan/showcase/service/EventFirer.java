package de.gedoplan.showcase.service;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

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