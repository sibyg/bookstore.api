package com.siby.assignment.hsbc.bookstore.api.repository;


import java.util.Collection;

import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import com.siby.assignment.hsbc.bookstore.api.repository.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDto, Long> {
    Collection<UserDto> findByEmail(String email);
}