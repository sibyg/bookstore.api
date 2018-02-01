package com.siby.assignment.hsbc.bookstore.api.security.service;

import com.siby.assignment.hsbc.bookstore.api.security.model.User;
import com.siby.assignment.hsbc.bookstore.api.security.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}