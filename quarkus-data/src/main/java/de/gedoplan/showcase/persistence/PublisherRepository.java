package de.gedoplan.showcase.persistence;

import java.util.Optional;

import de.gedoplan.showcase.entity.Publisher;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;


@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Integer> {
  // TODO This should work without annotation
  @Query("select count(x) from Publisher x")
  long count();

    // TODO This method is in BasicRepository; it should work without being repeated here
  @Find
  Optional<Publisher> findById(Integer id);
}
