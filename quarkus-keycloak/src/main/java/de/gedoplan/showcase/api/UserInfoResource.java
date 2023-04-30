package de.gedoplan.showcase.api;

import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

@Path("user-info")
@RequestScoped
public class UserInfoResource {
  @Inject
  JsonWebToken jwt;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
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
  @Path("name")
  @Produces(MediaType.TEXT_PLAIN)
  public Response getUserName() {
    return Stream.of(Claims.upn, Claims.preferred_username, Claims.sub)
      .map(jwt::getClaim)
      .filter(username -> username != null)
      .findFirst()
      .map(username -> Response.ok(username).build())
      .orElse(Response.noContent().build());
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
