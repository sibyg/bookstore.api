package com.siby.assignment.hsbc.bookstore.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Book {

    @JsonIgnore
    @ManyToOne
    private Bookstore bookstore;

    @Id
    @GeneratedValue
    private Long id;

    public String author;
    public String title;

    public Bookstore getBookstore() {
        return bookstore;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Book() { // jpa only
    }

    public Book(Bookstore bookstore, String author, String title) {
        this.author = author;
        this.title = title;
        this.bookstore = bookstore;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "-" + getId();
    }
}