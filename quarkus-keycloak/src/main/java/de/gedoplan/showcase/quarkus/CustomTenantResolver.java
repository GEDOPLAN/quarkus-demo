package de.gedoplan.showcase.quarkus;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;

/**
 * Resolver for OIDC tenant.
 *
 * If the request path starts with <code>api</code>, the tenant is <code>api</code>, <code>null</code> otherwise.
 *
 * Note: Path prefix and tenant are hard coded!
 */
@ApplicationScoped
public class CustomTenantResolver implements TenantResolver {

  @Override
  public String resolve(RoutingContext context) {
    String path = context.request().path();
    return path.startsWith("/api") ? "api" : null;
  }
}