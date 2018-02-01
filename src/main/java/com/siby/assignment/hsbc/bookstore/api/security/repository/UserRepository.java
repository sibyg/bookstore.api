package com.siby.assignment.hsbc.bookstore.api.security.repository;


import com.siby.assignment.hsbc.bookstore.api.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}