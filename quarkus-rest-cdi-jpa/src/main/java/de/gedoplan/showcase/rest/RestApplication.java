package de.gedoplan.showcase.rest;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@Dependent
@ApplicationPath(RestApplication.PATH)
public class RestApplication extends Application {
  public static final String PATH = "/";
}
