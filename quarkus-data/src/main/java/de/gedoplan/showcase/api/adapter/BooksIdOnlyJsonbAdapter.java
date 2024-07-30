package de.gedoplan.showcase.api.adapter;

import java.util.Set;
import java.util.stream.Collectors;

import de.gedoplan.showcase.entity.Book;
import jakarta.json.bind.adapter.JsonbAdapter;

public class BooksIdOnlyJsonbAdapter implements JsonbAdapter<Set<Book>, Set<Integer>> {

  @Override
  public Set<Book> adaptFromJson(Set<Integer> ids) {
    throw new UnsupportedOperationException("Unimplemented method 'adaptFromJson'");
  }

  @Override
  public Set<Integer> adaptToJson(Set<Book> books) {
    return books == null
        ? null
        : books
            .stream()
            .map(Book::getId)
            .collect(Collectors.toSet());
  }

}
