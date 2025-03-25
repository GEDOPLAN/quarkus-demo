package de.gedoplan.showcase.service;

import de.gedoplan.showcase.model.Trainer;
import de.gedoplan.showcase.persistence.TrainerRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class DemoDataService {

  @Inject
  TrainerRepository trainerRepository;

  @Transactional
  void initDemoData(@Observes StartupEvent startupEvent) {
    if (this.trainerRepository.countAll() == 0) {
      this.trainerRepository.persist(new Trainer("dw", "Dirk Weil", "JSE", "JEE", "K8s"));
      this.trainerRepository.persist(new Trainer("mp", "Markus Pauer", "JSE", "JEE"));
      this.trainerRepository.persist(new Trainer("hj", "Hendrik Jungnitsch", "SBT", "K8s"));
    }
  }
}
