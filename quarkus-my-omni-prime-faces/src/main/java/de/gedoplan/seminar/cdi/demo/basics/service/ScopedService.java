package de.gedoplan.seminar.cdi.demo.basics.service;

import lombok.Getter;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public abstract class ScopedService implements Serializable {
  @Inject
  Logger logger;

  protected int instanceNumber;

  protected static final Set<ScopedService> INSTANCES = Collections.synchronizedSet(new HashSet<>());

  public String getInstancesAsString() {
    return INSTANCES.stream()
      .filter(i -> i.getClass()==getClass())
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
    return instanceNumber == that.instanceNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(instanceNumber);
  }

  @PostConstruct
  public void init() {
    logger.debugf("Created %s", this);
    INSTANCES.add(this);
  }

  @PreDestroy
  public void cleanup() {
    logger.debugf("Destroy %s", this);
    INSTANCES.remove(this);
  }
}
