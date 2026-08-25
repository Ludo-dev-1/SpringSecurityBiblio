package fr.simplon.springsecuritybiblio.controller;

import fr.simplon.springsecuritybiblio.model.Book;
import fr.simplon.springsecuritybiblio.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookServiceInjected;

    public BookController(BookService bookService) {
        this.bookServiceInjected = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {return ResponseEntity.ok(bookServiceInjected.findAll());}

    @PostMapping
    public Book save(@RequestBody Book book) {return bookServiceInjected.create(book);}

}
