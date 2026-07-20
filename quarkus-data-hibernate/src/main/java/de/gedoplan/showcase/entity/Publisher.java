package de.gedoplan.showcase.entity;

import io.quarkus.hibernate.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Publisher.TABLE_NAME)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Publisher extends PanacheEntity {
  public static final String TABLE_NAME = "JPA_PUBLISHER";
  public static final String CATEGORIES_TABLE_NAME = "JPA_PUBLISHER_CATEGORIES";

  public static final String COUNTRY_FK_NAME = "COUNTRY_ISO_CODE";

  private String name;

  // TODO LAZY is not usable and leads to LazyInitializationExceptions all the time
  @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
  private Set<Book> books;

  public Publisher(String name) {
    this.name = name;

    this.books = new HashSet<>();
  }

  void addBook(Book book) {
    this.books.add(book);
  }

  void removeBook(Book book) {
    this.books.remove(book);
  }
}
