package de.gedoplan.showcase.service;

import java.util.Collection;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;

import lombok.Getter;

@RequestScoped
public class UserInfoService {

  @Getter
  private String userName = "someUser";

  @Getter
  private Collection<String> roles = List.of("demoRole");

  public boolean isLoggedIn() {
    return this.userName != null;
  }

}
