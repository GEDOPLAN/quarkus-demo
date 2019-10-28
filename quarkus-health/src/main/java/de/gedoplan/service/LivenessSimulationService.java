package de.gedoplan.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
public class LivenessSimulationService {

  @Inject
  @ConfigProperty(name = "health.live", defaultValue = "true")
  @Getter
  boolean live;
}
