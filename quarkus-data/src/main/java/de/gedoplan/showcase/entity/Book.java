package de.gedoplan.showcase.entity;

import de.gedoplan.showcase.api.adapter.PublisherIdOnlyJsonbAdapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Book.TABLE_NAME)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
  public static final String TABLE_NAME = "JPA_BOOK";
  public static final String AUTHORS_TABLE_NAME = "JPA_BOOK_AUTHORS";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String isbn;
  private int pages;

  @ManyToOne
  @Getter(AccessLevel.NONE)
  private Publisher publisher;

  public Book(String name, String isbn, int pages) {
    this.name = name;
    this.isbn = isbn;
    this.pages = pages;
  }

  @JsonbTypeAdapter(PublisherIdOnlyJsonbAdapter.class)
  public Publisher getPublisher() {
    return publisher;
  }

  public void changePublisher(Publisher publisher) {
    if (this.publisher != null) {
      this.publisher.removeBook(this);
    }

    this.publisher = publisher;

    if (this.publisher != null) {
      this.publisher.addBook(this);
    }
  }
}
