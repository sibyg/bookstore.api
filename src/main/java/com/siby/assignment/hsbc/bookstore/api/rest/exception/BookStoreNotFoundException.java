package com.siby.assignment.hsbc.bookstore.api.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookStoreNotFoundException extends RuntimeException {

    public BookStoreNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}