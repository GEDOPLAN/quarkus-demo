package de.gedoplan.showcase.service;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;

@WebServlet(loadOnStartup = 1)
public class LifecycleServlet extends HttpServlet {

  @Inject
  Log log;

  @Override
  public void destroy() {
    this.log.info("Servlet destroy");
  }

  @Override
  public void init() throws ServletException {
    this.log.info("Servlet init");
  }

}
