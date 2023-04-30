package de.gedoplan.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
public class LivenessSimulationService {

  @Inject
  @ConfigProperty(name = "health.live", defaultValue = "true")
  @Getter
  boolean live;
}
