package de.gedoplan.showcase.api;

import de.gedoplan.showcase.entity.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

@ApplicationScoped
@Path("person")
public class PersonEndpoint {
  @Inject
  Log log;

  @Inject
  @Channel("posted-person")
  Emitter<String> emitter;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void post(Person person) {
    this.log.debug("post(" + person + ")");

    /*
     * TODO AMQP akzeptiert keine serialisierten Objekte als Payload, daher Wandlung in JSON.
     * Besser wäre, wenn dies durch den Channel verkapselt wäre
     */
    String personJson = JsonbBuilder.create().toJson(person);
    this.emitter.send(personJson);
  }

}
