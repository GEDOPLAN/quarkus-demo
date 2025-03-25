package de.gedoplan.showcase.service;

import de.gedoplan.showcase.model.Room;
import de.gedoplan.showcase.persistence.RoomRepository;

import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DemoDataService {

  @Inject
  RoomRepository roomRepository;

  @Inject
  Logger logger;

  @Transactional
  void initDemoData(@Observes StartupEvent startupEvent) {
    if (this.roomRepository.countAll() == 0) {
      this.roomRepository.persist(new Room("WB", "Willy Brandt", "B", 6));
      this.roomRepository.persist(new Room("RVW", "Richard von Weizsäcker", "B", 12));
      this.roomRepository.persist(new Room("BBT", "Brandenburger Tor", "B", 6));
      this.roomRepository.persist(new Room("PB", "Paderborn", "BI", 12));
      this.roomRepository.persist(new Room("DT", "Detmold", "BI", 6));
    }
  }
}
