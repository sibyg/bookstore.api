package com.siby.assignment.hsbc.bookstore.api.web;

import com.siby.assignment.hsbc.bookstore.api.web.exception.BookStoreAlreadyExistsException;
import com.siby.assignment.hsbc.bookstore.api.web.exception.BookStoreNotFoundException;
import com.siby.assignment.hsbc.bookstore.security.web.exception.EmailExistsException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class BookstoreControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BookStoreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors bookStoreNotFoundExceptionHandler(BookStoreNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BookStoreAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors bookStoreAlreadyExistsHandler(BookStoreAlreadyExistsException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors emailAlreadyExistsHandler(EmailExistsException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
