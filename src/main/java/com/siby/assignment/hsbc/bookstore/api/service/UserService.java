package com.siby.assignment.hsbc.bookstore.api.service;

import com.siby.assignment.hsbc.bookstore.api.repository.dto.UserDto;
import com.siby.assignment.hsbc.bookstore.api.rest.exception.EmailExistsException;

public class UserService implements IUserService {
    @Override
    public boolean registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
        return false;
    }
}
