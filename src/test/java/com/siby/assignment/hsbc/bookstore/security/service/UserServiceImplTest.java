package com.siby.assignment.hsbc.bookstore.security.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.Random;

import com.siby.assignment.hsbc.bookstore.security.model.Role;
import com.siby.assignment.hsbc.bookstore.security.model.User;
import com.siby.assignment.hsbc.bookstore.security.repository.UserRepository;
import com.siby.assignment.hsbc.bookstore.security.web.dto.UserRegistrationDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void shouldSaveUser() {
        // given
        UserRegistrationDto registration = randomUserRegistrationDto();

        // when
        userService.save(registration);

        // then
        verify(passwordEncoder).encode(registration.getPassword());
        // and
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argument.capture());
        User actualUser = argument.getValue();
        assertThat(actualUser.getFirstName(), is(registration.getFirstName()));
        assertThat(actualUser.getLastName(), is(registration.getLastName()));
        assertThat(actualUser.getEmail(), is(registration.getEmail()));

    }

    @Test
    public void shouldFindByEmail() {
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
    public void shouldThrowUsernameNotFoundException() {
        // given
        String email = randomString();
        // and
        given(userRepository.findByEmail(email)).willReturn(null);
        // and
        thrown.expect(UsernameNotFoundException.class);

        // when
        userService.loadUserByUsername(email);
    }

    @Test
    public void shouldLoadUserByEmail() {
        // given
        String email = randomString();
        // and
        User user = randomUser();
        given(userRepository.findByEmail(email)).willReturn(user);

        // when
        UserDetails userDetails = userService.loadUserByUsername(email);

        // then
        assertThat(userDetails.getUsername(), is(user.getEmail()));
        // and
        assertThat(userDetails.getPassword(), is(user.getPassword()));
    }

    private User randomUser() {
        User user = new User();
        String firstName = randomString();
        user.setFirstName(firstName);
        String lastName = randomString();
        user.setLastName(lastName);
        String email = randomString();
        user.setEmail(email);
        user.setPassword(randomString());
        user.setRoles(Collections.singletonList(new Role("USER")));
        return user;
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