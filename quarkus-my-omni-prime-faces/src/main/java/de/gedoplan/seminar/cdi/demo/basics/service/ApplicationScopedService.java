package de.gedoplan.seminar.cdi.demo.basics.service;

import org.jboss.logging.Logger;

import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationScopedService extends ScopedService {

  @Inject
  public ApplicationScopedService() {
    this.instanceCreated = new Date();
  }

  private Date instanceCreated;

  public Date getInstanceCreated() {
    return this.instanceCreated;
  }

  @Override
  public String toString() {
    return String.format("%s#%tT", getClass().getSimpleName(), this.instanceCreated);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ApplicationScopedService that = (ApplicationScopedService) o;
    return instanceCreated.equals(that.instanceCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), instanceCreated);
  }
}
