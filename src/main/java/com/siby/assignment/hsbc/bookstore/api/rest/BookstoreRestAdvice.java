package com.siby.assignment.hsbc.bookstore.api.rest;

import com.siby.assignment.hsbc.bookstore.api.rest.exception.BookStoreNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class BookmarkControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BookStoreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors bookStoreNotFoundExceptionHandler(BookStoreNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
