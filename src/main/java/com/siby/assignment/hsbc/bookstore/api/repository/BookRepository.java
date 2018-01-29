package com.siby.assignment.hsbc.bookstore.api.repository;


import java.util.Collection;

import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Collection<Book> findByBookstoreName(String name);
}