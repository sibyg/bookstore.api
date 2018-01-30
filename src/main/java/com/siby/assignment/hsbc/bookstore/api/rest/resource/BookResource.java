package com.siby.assignment.hsbc.bookstore.api.rest.resource;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import com.siby.assignment.hsbc.bookstore.api.rest.BookstoreRestController;
import org.springframework.hateoas.ResourceSupport;

public class BookResource extends ResourceSupport {

    private final Book book;

    public BookResource(Book book) {
        String bookstoreName = book.getBookstore().getName();
        this.book = book;
        this.add(linkTo(BookstoreRestController.class, bookstoreName).withRel("books"));
        this.add(linkTo(methodOn(BookstoreRestController.class, bookstoreName)
                .readBook(bookstoreName, book.getId())).withSelfRel());
    }

    public Book getBook() {
        return book;
    }
}