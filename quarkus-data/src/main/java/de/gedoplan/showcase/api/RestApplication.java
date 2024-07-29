package de.gedoplan.showcase.api;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@Dependent
@ApplicationPath("/")
public class RestApplication extends Application {
}
