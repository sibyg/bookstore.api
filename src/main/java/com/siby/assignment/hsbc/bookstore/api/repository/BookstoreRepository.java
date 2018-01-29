package com.siby.assignment.hsbc.bookstore.api.repository;

import java.util.Optional;

import com.siby.assignment.hsbc.bookstore.api.domain.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
    Optional<Bookstore> findByName(String name);
}