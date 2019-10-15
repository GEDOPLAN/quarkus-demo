package de.gedoplan.showcase.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.entity.Person;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class PersonRepository extends SingleIdEntityRepository<Integer, Person> {

  /*
   * TODO
   * Quarkus scheint nicht richtig in Basisklassen-Methoden zu injizieren - entityManager ist (manchmal?) null.
   * Wenn die betroffenen Methoden hier als Delegationsmethode enthalten sind, klappt's ...
   */
  @Override
  public long countAll() {
    return super.countAll();
  }

  @Override
  public Person findById(Integer id) {
    return super.findById(id);
  }

  @Override
  public List<Person> findAll() {
    return super.findAll();
  }

  @Override
  public Person merge(Person entity) {
    return super.merge(entity);
  }

  @Override
  public void persist(Person entity) {
    super.persist(entity);
  }

  @Override
  public boolean removeById(Integer id) {
    return super.removeById(id);
  }
}
