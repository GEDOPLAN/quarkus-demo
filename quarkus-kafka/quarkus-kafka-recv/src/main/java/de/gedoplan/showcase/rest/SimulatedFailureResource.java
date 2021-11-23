package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
