package de.gedoplan.showcase.webui;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.Principal;
import java.util.List;
import java.util.stream.Stream;

@RequestScoped
@Named
public class UserInfoPresenter {

  @Inject
  ExternalContext externalContext;

  public String getRemoteUser() {
    return externalContext.getRemoteUser();
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

  public void logout() {
    // ???
  }
}
