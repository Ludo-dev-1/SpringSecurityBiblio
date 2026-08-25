package fr.simplon.springsecuritybiblio.service;

import fr.simplon.springsecuritybiblio.model.Book;
import fr.simplon.springsecuritybiblio.repository.Bookrepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class BookService {

    private final Bookrepository bookrepositoryInjected;

    public BookService(Bookrepository bookrepositoryInjected) {
        this.bookrepositoryInjected = bookrepositoryInjected;
    }

    public List<Book> findAll() { return this.bookrepositoryInjected.findAll();}

    public Book create(@RequestBody Book book) {
        return this.bookrepositoryInjected.save(book);
    }
}
