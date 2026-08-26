package fr.simplon.springsecuritybiblio.service;

import fr.simplon.springsecuritybiblio.model.Book;
import fr.simplon.springsecuritybiblio.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class BookService {

    private final BookRepository bookRepositoryInjected;

    public BookService(BookRepository bookRepositoryInjected) {
        this.bookRepositoryInjected = bookRepositoryInjected;
    }

    public List<Book> findAll() {
        return bookRepositoryInjected.findAll();
    }

    public Book create(Book book) {
        return bookRepositoryInjected.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return bookRepositoryInjected.findById(id);
    }

    public Book update(UUID id, Book bookUpdated) {

        Optional<Book> existingBook = bookRepositoryInjected.findById(id);

        if (existingBook.isEmpty()) {
            return null;
        }

        Book book = existingBook.get();
        book.setTitle(bookUpdated.getTitle());
        book.setAuthor(bookUpdated.getAuthor());
        book.setYearPublished(bookUpdated.getYearPublished());

        return bookRepositoryInjected.save(book);
    }

    public void delete(UUID id) {
        bookRepositoryInjected.deleteById(id);
    }


}
