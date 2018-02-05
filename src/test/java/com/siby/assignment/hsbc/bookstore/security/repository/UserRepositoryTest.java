package com.siby.assignment.hsbc.bookstore.security.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.siby.assignment.hsbc.bookstore.security.model.User;
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
        String email = "test@gmail.com";
        this.entityManager.persist(new User("firstname", "lastname", email, "password"));

        // when
        User actualUser = this.repository.findByEmail(email);

        // then
        assertThat(actualUser.getEmail(), is(email));
    }
}