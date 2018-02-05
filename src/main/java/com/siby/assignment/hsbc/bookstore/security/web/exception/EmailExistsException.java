package com.siby.assignment.hsbc.bookstore.security.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String email) {
        super("User email already exists: '" + email + "'.");
    }
}