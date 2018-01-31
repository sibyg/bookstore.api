package com.siby.assignment.hsbc.bookstore.api.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import com.siby.assignment.hsbc.bookstore.api.domain.Bookstore;
import com.siby.assignment.hsbc.bookstore.api.repository.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void shouldFindUserByEmail() throws Exception {
        // given
        UserDto user  = new UserDto();
    }
}