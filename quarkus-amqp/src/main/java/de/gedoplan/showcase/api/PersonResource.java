package de.gedoplan.showcase.api;

import de.gedoplan.showcase.entity.Person;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Path("persons")
public class PersonResource {
  @Inject
  Log log;

  @Inject
  @Channel("posted-person")
  Emitter<String> emitter;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response post(Person person) {
    this.log.debug("post(" + person + ")");

    /*
     * TODO AMQP akzeptiert keine serialisierten Objekte als Payload, daher Wandlung in JSON.
     * Besser wäre, wenn dies durch den Channel verkapselt wäre
     */
    String personJson = JsonbBuilder.create().toJson(person);
    this.emitter.send(personJson);

    return Response.status(201).build();
  }

}
