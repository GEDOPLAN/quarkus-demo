package de.gedoplan.showcase.presentation;

import de.gedoplan.showcase.service.ApplicationScopedService;
import de.gedoplan.showcase.service.DependentScopedService;
import de.gedoplan.showcase.service.OmniFacesViewScopedService;
import de.gedoplan.showcase.service.RequestScopedService;
import de.gedoplan.showcase.service.SessionScopedService;
import de.gedoplan.showcase.service.ViewScopedService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.Getter;

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
