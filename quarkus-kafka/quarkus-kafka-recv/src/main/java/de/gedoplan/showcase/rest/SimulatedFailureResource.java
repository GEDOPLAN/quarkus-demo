package de.gedoplan.showcase.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import de.gedoplan.showcase.service.PersonReceiver;

@ApplicationScoped
@Path("failure")
public class SimulatedFailureResource {
    
    @Inject
    PersonReceiver personReceiver;

    @Inject
    Logger logger;

    @GET
    @Produces("text/plain")
    public int getSimulatedFailureNumber() {
      return personReceiver.getSimulatedFailureNumber();
    }

    @PUT
    @Consumes("*/*")
    public void setSimulatedFailureNumber(@QueryParam("n") @DefaultValue("0") int n) {
      personReceiver.setSimulatedFailureNumber(n);;

      logger.tracef("simulatedFailureNumber=%d", personReceiver.getSimulatedFailureNumber());
    }
}
