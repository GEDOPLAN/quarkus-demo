package de.gedoplan.seminar.cdi.demo.basics.presentation;

import de.gedoplan.seminar.cdi.demo.basics.service.ApplicationScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.DependentScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.RequestScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.SessionScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.ViewScopedService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ScopePresenter2 {
  @Inject
  RequestScopedService requestScopedService;

  @Inject
  SessionScopedService sessionScopedService;

  @Inject
  ApplicationScopedService applicationScopedService;

  @Inject
  ViewScopedService viewScopedService;

  @Inject
  DependentScopedService dependentScopedService;

  public String getScopeInfo() {
    return String.format(
        "Request %02d, Session %02d, Application %tT, View %02d, Dependent %02d", this.requestScopedService.getInstanceNumber(), this.sessionScopedService.getInstanceNumber(),
        this.applicationScopedService.getInstanceCreated(), this.viewScopedService.getInstanceNumber(), this.dependentScopedService.getInstanceNumber());
  }
}
