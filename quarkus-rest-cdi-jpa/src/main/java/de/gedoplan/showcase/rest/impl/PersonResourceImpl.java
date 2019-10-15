package de.gedoplan.showcase.rest.impl;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;
import de.gedoplan.showcase.rest.PersonResource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;

@ApplicationScoped
@Path(PersonResourceImpl.PATH)
public class PersonResourceImpl implements PersonResource {

  @Inject
  PersonRepository personRepository;

  @Inject
  Log log;

  @Override
  public List<Person> getAll() {
    return this.personRepository.findAll();
  }

  @Override
  public Person getById(@PathParam(ID_NAME) Integer id) {
    Person person = this.personRepository.findById(id);
    if (person != null) {
      return person;
    }

    throw new NotFoundException();
  }

  @Override
  public void updatePerson(@PathParam(ID_NAME) Integer id, Person Person) {
    if (!id.equals(Person.getId())) {
      throw new BadRequestException("id of updated object must be unchanged");
    }

    this.personRepository.merge(Person);
  }

  @Override
  public Response createPerson(Person Person, @Context UriInfo uriInfo) throws URISyntaxException {
    if (Person.getId() != null) {
      throw new BadRequestException("id of new entry must not be set");
    }

    this.personRepository.persist(Person);

    URI createdUri = uriInfo.getAbsolutePathBuilder().path(ID_TEMPLATE).resolveTemplate(ID_NAME, Person.getId()).build();
    return Response.created(createdUri).build();
  }

  @Override
  public void deletePerson(@PathParam(ID_NAME) Integer id) {
    this.personRepository.removeById(id);
  }

  @PostConstruct
  void postConstruct() {
    this.log.debug("postConstruct");
  }

  @PreDestroy
  void preDestroy() {
    this.log.debug("preDestroy");
  }

}
