package de.gedoplan.showcase.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

import org.jboss.logging.Logger;

public abstract class ScopedService implements Serializable {
  @Inject
  Logger logger;

  protected int instanceNumber;

  protected static final Set<ScopedService> INSTANCES = Collections.synchronizedSet(new HashSet<>());

  public String getInstancesAsString() {
    return INSTANCES.stream()
      .filter(i -> i.getClass() == getClass())
      .map(Object::toString)
      .sorted()
      .collect(Collectors.joining(", "));
  }

  @Override
  public String toString() {
    return String.format("%s#%s", getClass().getSimpleName(), this.instanceNumber);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScopedService that = (ScopedService) o;
    return this.instanceNumber == that.instanceNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.instanceNumber);
  }

  @PostConstruct
  public void init() {
    this.logger.debugf("Created %s", this);
    INSTANCES.add(this);
  }

  @PreDestroy
  public void cleanup() {
    this.logger.debugf("Destroy %s", this);
    INSTANCES.remove(this);
  }
}
