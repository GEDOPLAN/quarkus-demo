package de.gedoplan.showcase.api;

import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Date;
import java.util.Set;

@Path("user-info")
@RequestScoped
public class UserInfoResource {
  @Inject
  JsonWebToken jwt;

  @GET
  @Path("info")
  public JsonObject info() {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    Set<String> claimNames = this.jwt.getClaimNames();
    if (claimNames != null) {
      for (String claimName : claimNames) {
        addJsonProperty(jsonObjectBuilder, claimName);
      }
    } else {
      addJsonProperty(jsonObjectBuilder, Claims.upn.toString(), "anonymous");
    }
    return jsonObjectBuilder.build();
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

  private void addJsonProperty(JsonObjectBuilder jsonObjectBuilder, String name) {
    Object claim = this.jwt.getClaim(name);
    if (claim != null) {
      switch (name) {
      case "exp":
      case "iat":
      case "auth_time":
      case "updated_at":
        claim = String.format("%s (%tF %<tT %<tZ)", claim, new Date(Long.parseLong(claim.toString()) * 1000L));
      }
    }
    addJsonProperty(jsonObjectBuilder, name, claim);
  }

  private static void addJsonProperty(JsonObjectBuilder jsonObjectBuilder, String name, Object value) {
    if (value != null) {
      jsonObjectBuilder.add(name, value.toString());
    } else {
      jsonObjectBuilder.addNull(name);
    }
  }
}
