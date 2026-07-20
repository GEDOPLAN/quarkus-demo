package de.gedoplan.showcase.entity;

import io.quarkus.hibernate.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Book.TABLE_NAME)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends PanacheEntity {
  public static final String TABLE_NAME = "JPA_BOOK";
  public static final String AUTHORS_TABLE_NAME = "JPA_BOOK_AUTHORS";

  private String name;
  private String isbn;
  private int pages;

  @ManyToOne
  private Publisher publisher;

  public Book(String name, String isbn, int pages) {
    this.name = name;
    this.isbn = isbn;
    this.pages = pages;
  }
}
