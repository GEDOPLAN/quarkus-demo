package de.gedoplan.showcase.api;

import java.security.Principal;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("user-info")
@RequestScoped
public class UserInfoResource {
  @GET
  @Path("name")
  @Produces(MediaType.TEXT_PLAIN)
  public String getName(@Context SecurityContext securityContext) {
    Principal userPrincipal = securityContext.getUserPrincipal();
    return userPrincipal != null ? userPrincipal.getName() : null;
  }

  @GET
  @Path("restricted")
  @RolesAllowed("demoRole")
  @Produces(MediaType.TEXT_PLAIN)
  public String restricted() {
    return "OK";
  }

  @GET
  @Path("restricted2")
  @RolesAllowed("otherRole")
  @Produces(MediaType.TEXT_PLAIN)
  public String restricted2() {
    return "OK";
  }

  @GET
  @Path("secctx")
  @Produces(MediaType.APPLICATION_JSON)
  public JsonObject context(@Context SecurityContext securityContext) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    Principal userPrincipal = securityContext.getUserPrincipal();
    addJsonProperty(jsonObjectBuilder, "user", userPrincipal != null ? userPrincipal.getName() : null);
    addJsonProperty(jsonObjectBuilder, "demoRole", securityContext.isUserInRole("demoRole"));
    addJsonProperty(jsonObjectBuilder, "otherRole", securityContext.isUserInRole("otherRole"));
    return jsonObjectBuilder.build();
  }

  private static void addJsonProperty(JsonObjectBuilder jsonObjectBuilder, String name, Object value) {
    if (value != null) {
      jsonObjectBuilder.add(name, value.toString());
    } else {
      jsonObjectBuilder.addNull(name);
    }
  }
}
