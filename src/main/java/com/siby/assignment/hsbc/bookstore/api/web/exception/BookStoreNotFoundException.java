package com.siby.assignment.hsbc.bookstore.api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookStoreNotFoundException extends RuntimeException {

    public BookStoreNotFoundException(String bookstoreName) {
        super("could not find book store: '" + bookstoreName + "'.");
    }
}