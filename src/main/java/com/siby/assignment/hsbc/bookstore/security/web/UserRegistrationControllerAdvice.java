package com.siby.assignment.hsbc.bookstore.security.web;

import com.siby.assignment.hsbc.bookstore.security.web.exception.EmailExistsException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserRegistrationControllerAdvice {

    @ResponseBody
    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors emailAlreadyExistsHandler(EmailExistsException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
