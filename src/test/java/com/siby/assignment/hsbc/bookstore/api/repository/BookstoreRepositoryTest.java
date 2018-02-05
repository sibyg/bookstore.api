package com.siby.assignment.hsbc.bookstore.api.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import com.siby.assignment.hsbc.bookstore.api.model.Bookstore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookstoreRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookstoreRepository repository;

    @Test
    public void shouldFindBookstoreByName() throws Exception {
        // given
        String bookstoreName = "test123";
        this.entityManager.persist(new Bookstore(bookstoreName));

        // when
        Optional<Bookstore> bookstoreOptional = this.repository.findByName(bookstoreName);

        // then
        assertThat(bookstoreOptional.isPresent(), is(true));
        assertThat(bookstoreOptional.get().getId(), is(not(nullValue())));
    }
}