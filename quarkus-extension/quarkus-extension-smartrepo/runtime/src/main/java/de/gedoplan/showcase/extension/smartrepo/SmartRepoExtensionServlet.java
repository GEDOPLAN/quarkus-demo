package de.gedoplan.showcase.extension.smartrepo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmartRepoExtensionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
        resp.getWriter().write("Hello from " + this.getClass().getSimpleName());
    }
}