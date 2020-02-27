package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

@ApplicationScoped
@Path("message")
public class MessageEndpoint {
  @Inject
  Log log;

  @Inject
  @Channel("demo-channel-1")
  Emitter<String> emitter;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void post(String text) {
    this.log.debug("Posted " + text);
    this.emitter.send(text);
  }

}
