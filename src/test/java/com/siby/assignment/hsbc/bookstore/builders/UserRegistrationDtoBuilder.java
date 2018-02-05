package com.siby.assignment.hsbc.bookstore.builders;

import static com.siby.assignment.hsbc.bookstore.random.RandomGenerator.EMAIL_ADDRESS;
import static com.siby.assignment.hsbc.bookstore.random.RandomGenerator.STRING;

import com.siby.assignment.hsbc.bookstore.security.web.dto.UserRegistrationDto;

public class UserRegistrationDtoBuilder {
    private String firstName = STRING.next();

    private String lastName = STRING.next();

    private String password = STRING.next();

    private String confirmPassword = password;

    private String email = EMAIL_ADDRESS.next();

    private String confirmEmail = email;

    private Boolean terms = true;

    private UserRegistrationDtoBuilder() {
    }

    public static UserRegistrationDtoBuilder instance() {
        return new UserRegistrationDtoBuilder();
    }

    public UserRegistrationDtoBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRegistrationDtoBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRegistrationDtoBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserRegistrationDtoBuilder confirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public UserRegistrationDtoBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserRegistrationDtoBuilder confirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
        return this;
    }

    public UserRegistrationDtoBuilder terms(Boolean terms) {
        this.terms = terms;
        return this;
    }


    public UserRegistrationDto build() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName(firstName);
        userRegistrationDto.setLastName(lastName);
        userRegistrationDto.setEmail(email);
        userRegistrationDto.setConfirmEmail(confirmEmail);
        userRegistrationDto.setPassword(password);
        userRegistrationDto.setConfirmPassword(confirmPassword);
        userRegistrationDto.setTerms(terms);
        return userRegistrationDto;
    }
}
