package de.gedoplan.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
public class ReadinessSimulationService {

  @Inject
  @ConfigProperty(name = "health.ready", defaultValue = "true")
  @Getter
  boolean ready;

}
