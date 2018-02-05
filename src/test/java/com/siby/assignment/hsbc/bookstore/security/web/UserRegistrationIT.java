package com.siby.assignment.hsbc.bookstore.security.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.siby.assignment.hsbc.bookstore.builders.UserRegistrationDtoBuilder;
import com.siby.assignment.hsbc.bookstore.random.RandomGenerator;
import com.siby.assignment.hsbc.bookstore.security.web.dto.UserRegistrationDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@ActiveProfiles("it")
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRegistrationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void submitRegistrationAccountExists() throws Exception {
        // given
        UserRegistrationDto userRegistrationDto = UserRegistrationDtoBuilder.instance().build();
        // and
        MockHttpServletRequestBuilder registrationRequest = createRegistrationRequest(userRegistrationDto);
        // and
        this.mockMvc.perform(registrationRequest);

        // when
        this.mockMvc.perform(registrationRequest)
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(status().isOk());
    }


    @Test
    public void submitRegistrationPasswordNotMatching() throws Exception {
        // given
        UserRegistrationDto userRegistrationDtoWithNonMatchingPassword = UserRegistrationDtoBuilder.instance()
                .confirmPassword(RandomGenerator.STRING.next())
                .build();
        // and
        MockHttpServletRequestBuilder registrationRequest = createRegistrationRequest(userRegistrationDtoWithNonMatchingPassword);

        // when
        this.mockMvc.perform(registrationRequest)
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationEmailNotMatching() throws Exception {
        // given
        UserRegistrationDto userRegistrationDtoWithNonMatchingEmail = UserRegistrationDtoBuilder.instance()
                .confirmEmail(RandomGenerator.STRING.next())
                .build();
        // and
        MockHttpServletRequestBuilder registrationRequest = createRegistrationRequest(userRegistrationDtoWithNonMatchingEmail);

        // when
        this.mockMvc
                .perform(registrationRequest)
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationSuccess() throws Exception {
        // given
        UserRegistrationDto us = UserRegistrationDtoBuilder.instance().build();
        // and
        MockHttpServletRequestBuilder registrationRequest = createRegistrationRequest(us);

        this.mockMvc
                .perform(registrationRequest)
                .andExpect(redirectedUrl("/registration?success"))
                .andExpect(status().is3xxRedirection());
    }

    private MockHttpServletRequestBuilder createRegistrationRequest(UserRegistrationDto userRegistrationDto) {
        return post("/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", userRegistrationDto.getFirstName())
                .param("lastName", userRegistrationDto.getLastName())
                .param("email", userRegistrationDto.getEmail())
                .param("confirmEmail", userRegistrationDto.getConfirmEmail())
                .param("password", userRegistrationDto.getPassword())
                .param("confirmPassword", userRegistrationDto.getConfirmPassword())
                .param("terms", userRegistrationDto.getTerms().toString());
    }
}