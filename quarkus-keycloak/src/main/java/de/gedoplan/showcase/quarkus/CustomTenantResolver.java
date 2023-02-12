package de.gedoplan.showcase.quarkus;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;

@ApplicationScoped
public class CustomTenantResolver implements TenantResolver {

  @Override
  public String resolve(RoutingContext context) {
    String path = context.request().path();
    return path.startsWith("/api") ? "api" : null;
  }
}