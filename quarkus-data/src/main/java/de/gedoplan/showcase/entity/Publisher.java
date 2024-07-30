package de.gedoplan.showcase.entity;

import java.util.HashSet;
import java.util.Set;

import de.gedoplan.showcase.api.adapter.BooksIdOnlyJsonbAdapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Publisher.TABLE_NAME)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Publisher {
  public static final String TABLE_NAME = "JPA_PUBLISHER";
  public static final String CATEGORIES_TABLE_NAME = "JPA_PUBLISHER_CATEGORIES";

  public static final String COUNTRY_FK_NAME = "COUNTRY_ISO_CODE";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  // TODO LAZY is not usable and leads to LazyInitializationExceptions all the time
  @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
  @Getter(AccessLevel.NONE)
  private Set<Book> books;

  public Publisher(String name) {
    this.name = name;

    this.books = new HashSet<>();
  }

  @JsonbTypeAdapter(BooksIdOnlyJsonbAdapter.class)
  public Set<Book> getBooks() {
    // return Collections.unmodifiableSet(books);
    return books;
  }

  void addBook(Book book) {
    this.books.add(book);
  }

  void removeBook(Book book) {
    this.books.remove(book);
  }
}
