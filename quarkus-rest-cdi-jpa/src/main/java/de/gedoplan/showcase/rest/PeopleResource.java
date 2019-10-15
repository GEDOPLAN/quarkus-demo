package de.gedoplan.showcase.rest;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.CreationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

@Path("people")
@ApplicationScoped
public class PeopleResource {

  // Does not work ...
  // @Inject
  // @RestClient
  PersonResource personResource;

  @PostConstruct
  void postContruct() {
    URL url;
    try {
      url = new URL("http://localhost:8080");
    } catch (MalformedURLException e) {
      throw new CreationException(e);
    }
    this.personResource = RestClientBuilder.newBuilder().baseUrl(url).build(PersonResource.class);
  }

  @GET
  @Path("count")
  @Produces(MediaType.APPLICATION_JSON)
  public int getCount() {
    return this.personResource.getAll().size();
  }
}
