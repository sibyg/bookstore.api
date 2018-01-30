package com.siby.assignment.hsbc.bookstore.api.rest;

import java.net.URI;
import java.util.stream.Collectors;

import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import com.siby.assignment.hsbc.bookstore.api.repository.BookRepository;
import com.siby.assignment.hsbc.bookstore.api.repository.BookstoreRepository;
import com.siby.assignment.hsbc.bookstore.api.rest.exception.BookStoreNotFoundException;
import com.siby.assignment.hsbc.bookstore.api.rest.resource.BookResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/{bookstoreName}/books")
public class BookstoreRestController {

    private final BookRepository bookRepository;

    private final BookstoreRepository bookstoreRepository;

    @Autowired
    BookstoreRestController(BookRepository bookRepository,
                            BookstoreRepository bookstoreRepository) {
        this.bookRepository = bookRepository;
        this.bookstoreRepository = bookstoreRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resources<BookResource> readBooks(@PathVariable String bookstoreName) {
        this.validateBookstore(bookstoreName);
        return new Resources<>(this.bookRepository.findByBookstoreName(bookstoreName).stream().map(BookResource::new).collect(Collectors.toList()));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public BookResource readBook(@PathVariable String bookstoreName, @PathVariable Long bookId) {
        this.validateBookstore(bookstoreName);
        return new BookResource(this.bookRepository.findOne(bookId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@PathVariable String bookstoreName, @RequestBody Book book) {
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