package de.gedoplan.showcase.rest;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import de.gedoplan.showcase.entity.Person;

@ApplicationScoped
@Path("person")
public class PersonResource {
  @Inject
  Logger logger;

  @Inject
  @Channel("person")
  Emitter<Person> emitter;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void post(Person person) {
    this.logger.debugf("Send %s", person);
    this.emitter.send(person);
  }

  private AtomicInteger idGenerator = new AtomicInteger();

  @POST
  @Path("auto")
  @Consumes("*/*")
  public void autoPost() {
    post(new Person("Warrior", "Clone-" + this.idGenerator.incrementAndGet()));
  }

}
