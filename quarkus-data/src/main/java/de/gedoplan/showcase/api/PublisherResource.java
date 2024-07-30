package de.gedoplan.showcase.api;

import java.util.List;

import de.gedoplan.showcase.entity.Publisher;
import de.gedoplan.showcase.persistence.PublisherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path(PublisherResource.PATH)
@Transactional(rollbackOn = Exception.class)
public class PublisherResource {
  public static final String PATH = "publishers";
  public static final String ID_NAME = "id";
  public static final String ID_TEMPLATE = "{" + ID_NAME + "}";

  @Inject
  PublisherRepository publisherRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Publisher> getAll() {
    return this.publisherRepository.findAll().toList();
  }

  @GET
  @Path(ID_TEMPLATE)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getById(@PathParam(ID_NAME) Integer id) {
    Publisher publisher = this.publisherRepository.findById(id).orElseThrow(NotFoundException::new);
    boolean booksIsLoaded = Persistence.getPersistenceUtil().isLoaded(publisher, "books");

    // TODO Force loading of books failes if books is LAZY
    publisher.getBooks().size();
    
    return Response
    .ok(publisher)
    .header("x-books-is-loaded", booksIsLoaded)
    .build();
  }

}