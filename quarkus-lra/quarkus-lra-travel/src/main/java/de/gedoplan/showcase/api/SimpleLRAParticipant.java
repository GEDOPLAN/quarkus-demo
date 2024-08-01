package de.gedoplan.showcase.api;

import java.net.URI;

import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/simple")
@ApplicationScoped
public class SimpleLRAParticipant {

    @Inject
    Logger logger;

    @LRA(LRA.Type.REQUIRES_NEW) // a new LRA is created on method entry
    @Path("/work")
    @PUT
    public void work(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {

        logger.infof("work: lraId=%s", lraId);

        /*
         * Perform business actions in the context of the LRA identified by the
         * value in the injected Jakarta REST header. This LRA was started just before
         * the method was entered (REQUIRES_NEW) and will be closed when the
         * method finishes at which point the completeWork method below will be
         * invoked.
         */
    }

    @Complete
    @AfterLRA
    @Path("/complete")
    @PUT
    public Response completeWork(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId, String userData) {

        logger.infof("completeWork: lraId=%s, userData=%s", lraId, userData);

        /*
         * Free up resources allocated in the context of the LRA identified by the
         * value in the injected Jakarta REST header.
         *
         * Since there is no @Status method in this class, completeWork MUST be
         * idempotent and MUST return the status.
         */
        return Response.ok(ParticipantStatus.Completed.name()).build();
    }

    @Compensate
    @Path("/compensate")
    @PUT
    public Response compensateWork(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId, String userData) {

        logger.infof("compensateWork: lraId=%s, userData=%s", lraId, userData);

        /*
         * The LRA identified by the value in the injected Jakarta REST header was
         * cancelled so the business logic should compensate for any actions
         * that have been performed while running in its context.
         *
         * Since there is no @Status method in this class, compensateWork MUST be
         * idempotent and MUST return the status
         */
        return Response.ok(ParticipantStatus.Compensated.name()).build();
    }
}