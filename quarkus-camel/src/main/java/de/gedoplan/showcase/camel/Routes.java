package de.gedoplan.showcase.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Routes extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    restConfiguration().bindingMode(RestBindingMode.json);

    rest("/api")
      .get("/persons")
      .to("bean:personRepository?method=findAll")

      .get("/persons/{id}")
      .to("bean:personRepository?method=findById(${header.id})");
  }
}
