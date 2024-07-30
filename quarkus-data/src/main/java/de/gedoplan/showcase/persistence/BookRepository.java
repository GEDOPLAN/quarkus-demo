package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.entity.Book;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;


@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
