package de.gedoplan.showcase.service;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

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
