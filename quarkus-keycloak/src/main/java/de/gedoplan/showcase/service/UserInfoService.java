package de.gedoplan.showcase.service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestScoped
public class UserInfoService {

  @Inject
  JsonWebToken jwt;

  @Inject
  Logger logger;

  @Getter
  private String clientId;

  @Getter
  private String userName;

  @Getter
  private Collection<String> roles;

  private static final Configuration JSON_PARSE_CONFIGURATION = Configuration.builder().options(Option.DEFAULT_PATH_LEAF_TO_NULL, Option.SUPPRESS_EXCEPTIONS).build();

  @PostConstruct
  void init() {
    if (this.jwt != null && this.jwt.getClaimNames() != null) {
      this.jwt.getClaimNames().forEach(n -> this.logger.debugf("JWT %S: %s", n, this.jwt.getClaim(n)));

      this.clientId
        = Stream.of("client_id", "azp")
        .map(this.jwt::getClaim)
        .filter(Objects::nonNull)
        .map(Object::toString)
        .findFirst()
        .orElse(null);

      this.logger.debugf("clientId: %s", this.clientId);

      this.userName
        = Stream.of(Claims.upn, Claims.preferred_username, Claims.sub)
        .map(this.jwt::getClaim)
        .filter(Objects::nonNull)
        .map(Object::toString)
        .findFirst()
        .orElse(null);

      this.logger.debugf("userName: %s", this.userName);

      this.roles = this.jwt.getGroups();
      if (this.roles.isEmpty()) {
        addRole("realm_access", "$.roles");
        addRole("resource_access", "$." + this.clientId + ".roles");
      }

      this.logger.debugf("roles: %s", this.roles.stream().collect(Collectors.joining(", ", "[", "]")));
    }
  }

  private void addRole(String claim, String jsonPath) {
    JsonObject jsonObject = this.jwt.getClaim(claim);
    if (jsonObject != null) {
      addRoles(JsonPath.using(JSON_PARSE_CONFIGURATION).parse(jsonObject).<JsonArray> read(jsonPath));
    }
  }

  private void addRoles(Iterable<?> src) {
    if (src != null) {
      for (Object elem : src) {
        String role = elem.toString();
        if (role.startsWith("\"") && role.endsWith("\"")) {
          role = role.substring(1, role.length() - 1);
        }
        this.roles.add(role);
      }
    }
  }

  public boolean isLoggedIn() {
    return this.userName != null;
  }

  //  public Collection<String> getRoles() {
  //    Configuration configuration = Configuration.builder().options(Option.DEFAULT_PATH_LEAF_TO_NULL, Option.SUPPRESS_EXCEPTIONS).build();
  //
  //    JsonObject realm_access = this.jwt.getClaim("realm_access");
  //    if (realm_access != null) {
  //      this.logger.debugf("realm_access: %s (%s)", realm_access, realm_access.getClass().toString());
  //
  //      List<String> realm_roles = JsonPath.read(realm_access, "$.roles");
  //      //      JsonArray realm_roles = realm_access.getJsonArray("roles");
  //      this.logger.debugf("realm_roles: %s", realm_roles);
  //    }
  //
  //    JsonObject resource_access = this.jwt.getClaim("resource_access");
  //    if (resource_access != null) {
  //      this.logger.debugf("resource_access: %s (%s)", resource_access, resource_access.getClass().toString());
  //
  //      String clientId = this.jwt.getClaim("client_id");
  //      if (clientId == null) {
  //        clientId = this.jwt.getClaim("azp");
  //      }
  //      this.logger.debugf("clientId: %s", clientId);
  //
  //      //      String jsonPath = "$['" + clientId + "']['roles']";
  //      String jsonPath = "$." + clientId + ".roles";
  //      this.logger.debugf("jsonPath: %s", jsonPath);
  //
  //      List<String> resource_roles = JsonPath.using(configuration).parse(resource_access).read(jsonPath);
  //      //      JsonArray resource_roles = resource_access.getJsonObject(clientId).getJsonArray("roles");
  //      this.logger.debugf("resource_roles: %s", resource_roles);
  //    }
  //
  //    return List.of();
  //  }
}
