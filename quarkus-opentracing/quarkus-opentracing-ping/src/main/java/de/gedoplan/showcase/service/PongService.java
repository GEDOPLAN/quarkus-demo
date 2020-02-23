package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.opentracing.Traced;

@ApplicationScoped
public class PongService {

  @Traced
  public String getPing() {
    return "Ping!";
  }
}
