package de.gedoplan.showcase.webui;

import de.gedoplan.showcase.service.UserInfoService;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestScoped
@Named
public class UserInfoPresenter {

  @Inject
  ExternalContext externalContext;

  @Inject
  UserInfoService userInfoService;

  @Inject
  Logger logger;

  public boolean isLoggedIn() {
    return this.userInfoService.isLoggedIn();
  }

  public String getUserName() {
    return this.userInfoService.getUserName();
  }

  public String getRoles() {
    Collection<String> roles = this.userInfoService.getRoles();
    return roles != null ? roles.stream().sorted().collect(Collectors.joining(", ")) : null;
  }

  public List<String> getAllRoles() {
    return Stream.of("demoRole", "otherRole", "specialRole")
      .toList();
  }

  public List<String> getUserRoles() {
    return getAllRoles()
      .stream()
      .filter(n -> this.externalContext.isUserInRole(n))
      .toList();
  }

  public void logout() throws IOException {
    this.externalContext.redirect("/logout");
  }
}
