package de.gedoplan.showcase.rest;

import javax.enterprise.context.Dependent;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Dependent
@ApplicationPath(RestApplication.PATH)
public class RestApplication extends Application {
  public static final String PATH = "";
}
