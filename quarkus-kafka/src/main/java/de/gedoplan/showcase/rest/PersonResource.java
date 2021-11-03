package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import de.gedoplan.showcase.entity.Person;

@ApplicationScoped
@Path("person")
public class PersonResource {
  @Inject
  Log log;

  @Inject
  @Channel("posted-person")
  Emitter<Person> emitter;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void post(Person person) {
    this.log.debug("post(" + person + ")");
    this.emitter.send(person);
  }

}
