package com.siby.assignment.hsbc.bookstore.api.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Bookstore {

    @OneToMany(mappedBy = "bookstore")
    private Set<Book> Books = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    public Set<Book> getBooks() {
        return Books;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String name;

    public Bookstore(String name) {
        this.name = name;
    }

    Bookstore() { // jpa only
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "-" + getId();
    }
}