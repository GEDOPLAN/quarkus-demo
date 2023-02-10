package de.gedoplan.showcase.webui;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Stream;

@RequestScoped
@Named
public class UserInfoPresenter {

  @Inject
  ExternalContext externalContext;

  @Inject
  JsonWebToken jwt;

  @Inject
  Logger logger;

  public String getRemoteUser() {
    String remoteUserFromExternalContext = externalContext.getRemoteUser();
    logger.debugf("remoteUserFromExternalContext: %s", remoteUserFromExternalContext);
    if (jwt!=null && jwt.getClaimNames()!=null) {
      jwt.getClaimNames().forEach(n -> logger.debugf("JWT %S: %s", n, jwt.getClaim(n)));
    }
    return remoteUserFromExternalContext;
  }

  public List<String> getAllRoles() {
    return Stream.of("demoRole", "otherRole")
      .toList();
  }

  public List<String> getUserRoles() {
    return getAllRoles()
      .stream()
      .filter(n -> externalContext.isUserInRole(n))
      .toList();
  }

  public void logout() throws IOException {
    externalContext.redirect("/logout");
  }
}
