package de.gedoplan.showcase.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Traced
public class PongService {

  @Inject
  @RestClient
  PongApi pongApi;

  public String getReply() {
    return "Reply: " + this.pongApi.getPong();
  }
}
