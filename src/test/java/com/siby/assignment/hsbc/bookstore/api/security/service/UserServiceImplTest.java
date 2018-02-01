package com.siby.assignment.hsbc.bookstore.api.security.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Random;

import com.siby.assignment.hsbc.bookstore.api.security.model.User;
import com.siby.assignment.hsbc.bookstore.api.security.repository.UserRepository;
import com.siby.assignment.hsbc.bookstore.api.security.web.dto.UserRegistrationDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void shouldfindByEmail() {
        // given
        String email = "test@gmail.com";
        // and
        User expectedUser = new User();
        // and
        given(userRepository.findByEmail(email)).willReturn(expectedUser);

        // when
        User actualUser = userService.findByEmail(email);

        // then
        assertThat(actualUser, is(expectedUser));
    }

    @Test
    public void shouldSaveUser() {
        UserRegistrationDto registration = randomUserRegistrationDto();
        userService.save(registration);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        User capture = captor.capture();
        verify(userRepository).save(capture);
        assertThat(capture.getFirstName(), is(registration.getFirstName()));
        assertThat(capture.getLastName(), is(registration.getLastName()));
        assertThat(capture.getEmail(), is(registration.getEmail()));
    }

    private UserRegistrationDto randomUserRegistrationDto() {
        UserRegistrationDto registration = new UserRegistrationDto();
        String firstName = randomString();
        registration.setFirstName(firstName);
        String lastName = randomString();
        registration.setLastName(lastName);
        String email = randomString();
        registration.setEmail(email);
        registration.setConfirmEmail(randomString());
        registration.setPassword(randomString());
        registration.setTerms(randomBoolean());
        return registration;
    }

    private Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    private String randomString() {
        return RandomStringUtils.random(3);
    }

}