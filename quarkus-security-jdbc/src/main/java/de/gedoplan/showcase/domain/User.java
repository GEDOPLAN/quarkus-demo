package de.gedoplan.showcase.domain;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;
import de.gedoplan.baselibs.persistence.entity.StringIdEntity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.quarkus.elytron.security.common.BcryptUtil;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SHOWCASE_USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends StringIdEntity {
  @Column(name = "CRYPTED_PWD")
  private String cryptedPwd;
  private String name;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "SHOWCASE_ROLES", foreignKey = @ForeignKey(name = "USER_ID"))
  @Column(name = "ROLE")
  private Set<String> roles;

  public User(String id, String pwd, String name, String ... roles) {
    super(id);
    this.cryptedPwd = BcryptUtil.bcryptHash(pwd);
    this.name = name;
    this.roles = Set.of(roles);
  }
}
