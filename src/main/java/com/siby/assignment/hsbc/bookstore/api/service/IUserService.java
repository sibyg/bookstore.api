package com.siby.assignment.hsbc.bookstore.api.service;

import com.siby.assignment.hsbc.bookstore.api.repository.dto.UserDto;
import com.siby.assignment.hsbc.bookstore.api.rest.exception.EmailExistsException;

public interface IUserService {
    boolean registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;
}