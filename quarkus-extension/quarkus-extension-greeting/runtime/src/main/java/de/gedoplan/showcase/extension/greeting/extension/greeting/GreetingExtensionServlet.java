package de.gedoplan.showcase.extension.greeting.extension.greeting;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GreetingExtensionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
        resp.getWriter().write("Hello from " + this.getClass().getSimpleName());
    }
}