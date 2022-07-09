package de.gedoplan.showcase.extension.greeting.extension.greeting;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GreetingExtensionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
        resp.getWriter().write("Hello from " + this.getClass().getSimpleName());
    }
}