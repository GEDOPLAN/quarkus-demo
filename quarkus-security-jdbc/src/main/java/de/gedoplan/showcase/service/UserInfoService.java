package de.gedoplan.showcase.service;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import io.quarkus.security.identity.CurrentIdentityAssociation;
import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.logging.Logger;

import lombok.Getter;

@RequestScoped
public class UserInfoService {

  @Inject
  void setHttpServletRequest(CurrentIdentityAssociation identityAssociation) {
    SecurityIdentity identity = identityAssociation.getIdentity();
    if (!identity.isAnonymous()) {
      this.userName = identity.getPrincipal().getName();

      this.roles = identity.getRoles();
    }
  }

  @Getter
  private String userName;

  @Getter
  private Collection<String> roles = Collections.emptySet();

  public boolean isLoggedIn() {
    return this.userName != null;
  }

}
