package de.gedoplan.showcase.rest;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

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
