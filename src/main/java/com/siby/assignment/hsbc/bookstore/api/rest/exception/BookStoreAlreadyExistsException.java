package com.siby.assignment.hsbc.bookstore.api.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookStoreAlreadyExistsException extends RuntimeException {

    public BookStoreAlreadyExistsException(String bookstoreName) {
        super("Book store already exists: '" + bookstoreName + "'.");
    }
}