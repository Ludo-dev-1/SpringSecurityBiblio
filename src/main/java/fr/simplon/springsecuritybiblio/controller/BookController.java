package fr.simplon.springsecuritybiblio.controller;

import fr.simplon.springsecuritybiblio.model.Book;
import fr.simplon.springsecuritybiblio.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookServiceInjected;

    public BookController(BookService bookService) {
        this.bookServiceInjected = bookService;
    }

    @PreAuthorize("hasAuthority ('SCOPE_ROLE_USER')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Book>> getAllBooks() {return ResponseEntity.ok(bookServiceInjected.findAll());}

    @PreAuthorize("hasAuthority ('SCOPE_ROLE_USER')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Optional<Book>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookServiceInjected.findById(id));
    }

    @PreAuthorize("hasAuthority ('SCOPE_ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@RequestBody Book book) {return bookServiceInjected.create(book);}

    @PreAuthorize("hasAuthority ('SCOPE_ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book update(@PathVariable UUID id, @RequestBody Book book) {
        Book bookUpdated = this.bookServiceInjected.update(id, book);
        if(bookUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return bookUpdated;
    }

    @PreAuthorize("hasAuthority ('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable UUID id) {
        bookServiceInjected.delete(id);
    }

}
