package de.gedoplan.showcase.quarkus;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.NewCookie;

import io.quarkus.security.identity.CurrentIdentityAssociation;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

  @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
  String cookieName;

  @Inject
  CurrentIdentityAssociation identity;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // If user is logged in, remove cookie Quarkus is using for form based auth
    if (!identity.getIdentity().isAnonymous()) {
      Cookie cookie = new Cookie(cookieName, "");
      cookie.setPath("/");
      // Max age 0 causes the cookie to be deleted
      cookie.setMaxAge(0);
      // Set expiry too, because some browsers do not delete cookies with max age 0
      cookie.setAttribute("Expires", "Thu, 1 Jan 1970 00:00:00 GMT");
      resp.addCookie(cookie);
    }

    resp.sendRedirect("/");
  }
}
