package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Book;
import de.gedoplan.showcase.entity.Publisher;
import de.gedoplan.showcase.persistence.BookRepository;
import de.gedoplan.showcase.persistence.PublisherRepository;

import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

@ApplicationScoped
public class InitPublisherDemoDataService {
  @Inject
  PublisherRepository publisherRepository;

  @Inject
  BookRepository bookRepository;

  @Inject
  Logger log;

  // Test data
  private Publisher testPublisher1 = new Publisher("O'Melly Publishing");
  private Publisher testPublisher2 = new Publisher("Expert Press");
  private Publisher testPublisher3 = new Publisher("Books reloaded");
  private Publisher[] testPublishers = { this.testPublisher1, this.testPublisher2, this.testPublisher3 };

  private Book testBook11 = new Book("Better JPA Programs", "12345-6789-0", 340);
  private Book testBook12 = new Book("Inside JPA", "54321-9876-X", 265);
  private Book testBook13 = new Book("Java and Databases", "11111-2222-6", 850);
  private Book testBook21 = new Book("Java for Beginners", "564534-432-2", 735);
  private Book testBook22 = new Book("Java vs. C#", "333333-123-0", 145);
  private Book testBook23 = new Book("Optimizing Java Programs", "765432-767-8", 230);
  private Book testBook30 = new Book("Is there a World after Java?", null, 1);
  private Book[] testBooks = { this.testBook11, this.testBook12, this.testBook13, this.testBook21, this.testBook22, this.testBook23, this.testBook30 };

  private Book[] testBooksOfPublisher1 = { this.testBook11, this.testBook12, this.testBook13 };
  private Book[] testBooksOfPublisher2 = { this.testBook21, this.testBook22, this.testBook23 };

  {
    for (Book book : this.testBooksOfPublisher1) {
      book.changePublisher(this.testPublisher1);
    }

    for (Book book : this.testBooksOfPublisher2) {
      book.changePublisher(this.testPublisher2);
    }
  }

  /**
   * Create test/demo data.
   * Attn: Interceptors may not be called, if method is private!
   *
   * @param event Application scope initialization event
   */
  @Transactional
  void createDemoData(@Observes Startup event) {
    try {
      if (this.publisherRepository.count() == 0) {
        // TODO Works only in order Publishers/Books, but not Books/Publishers
        Stream.of(this.testPublishers).forEach(this.publisherRepository::insert);
        Stream.of(this.testBooks).forEach(this.bookRepository::insert);
        log.debug("Created demo data");
      }
    } catch (Exception e) {
      log.warn("Cannot create demo data", e);
    }

  }

}
