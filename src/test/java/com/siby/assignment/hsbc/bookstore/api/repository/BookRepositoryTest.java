package com.siby.assignment.hsbc.bookstore.api.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import com.siby.assignment.hsbc.bookstore.api.domain.Bookstore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository repository;

    @Test
    public void shouldFindBooksByBookstoreName() throws Exception {
        // given
        String bookstoreName = "test1234";
        Bookstore bookstore = new Bookstore(bookstoreName, "password");
        this.entityManager.persist(bookstore);
        this.entityManager.persist(new Book(bookstore, "author", "title"));

        // when
        Collection<Book> books = this.repository.findByBookstoreName(bookstoreName);

        // then
        assertThat(books.isEmpty(), is(false));
        assertThat(books.iterator().next().getBookstore().name, is(bookstoreName));
    }
}