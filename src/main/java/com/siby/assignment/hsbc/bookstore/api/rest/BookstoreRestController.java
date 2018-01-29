package com.siby.assignment.hsbc.bookstore.api.rest;

import java.net.URI;
import java.util.Collection;

import com.siby.assignment.hsbc.bookstore.api.rest.exception.BookStoreNotFoundException;
import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import com.siby.assignment.hsbc.bookstore.api.repository.BookRepository;
import com.siby.assignment.hsbc.bookstore.api.repository.BookstoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/{bookstoreName}/books")
class BookRestController {

    private final BookRepository bookRepository;

    private final BookstoreRepository bookstoreRepository;

    @Autowired
    BookRestController(BookRepository bookRepository,
                       BookstoreRepository bookstoreRepository) {
        this.bookRepository = bookRepository;
        this.bookstoreRepository = bookstoreRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Book> readBooks(@PathVariable String bookstoreName) {
        this.validateBookstore(bookstoreName);
        return this.bookRepository.findByBookstoreName(bookstoreName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
    Book readBook(@PathVariable String bookstoreName, @PathVariable Long bookId) {
        this.validateBookstore(bookstoreName);
        return this.bookRepository.findOne(bookId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String bookstoreName, @RequestBody Book book) {
        this.validateBookstore(bookstoreName);

        return this.bookstoreRepository
                .findByName(bookstoreName)
                .map(bookstore -> {
                    Book result = bookRepository.save(new Book(bookstore,
                            book.author, book.title));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }

     void validateBookstore(String name) {
        this.bookstoreRepository.findByName(name).orElseThrow(
                () -> new BookStoreNotFoundException(name));
    }
}