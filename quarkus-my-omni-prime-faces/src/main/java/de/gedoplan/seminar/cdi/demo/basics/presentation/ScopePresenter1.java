package de.gedoplan.seminar.cdi.demo.basics.presentation;

import de.gedoplan.seminar.cdi.demo.basics.service.ApplicationScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.DependentScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.OmniFacesViewScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.RequestScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.SessionScopedService;
import de.gedoplan.seminar.cdi.demo.basics.service.ViewScopedService;
import lombok.Getter;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
@Getter
public class ScopePresenter1 {
  @Inject
  RequestScopedService requestScopedService;

  @Inject
  SessionScopedService sessionScopedService;

  @Inject
  ApplicationScopedService applicationScopedService;

  @Inject
  ViewScopedService viewScopedService;

  @Inject
  OmniFacesViewScopedService omniFacesViewScopedService;

  @Inject
  DependentScopedService dependentScopedService;
}
