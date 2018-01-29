package com.siby.assignment.hsbc.bookstore.api.domain;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


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

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String password;
    public String name;

    public Bookstore(String name, String password) {
        this.name = name;
        this.password = password;
    }

    Bookstore() { // jpa only
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "-" + getId();
    }
}