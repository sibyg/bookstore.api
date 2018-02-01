package com.siby.assignment.hsbc.bookstore.api.security.web;

import javax.validation.Valid;

import com.siby.assignment.hsbc.bookstore.api.security.model.User;
import com.siby.assignment.hsbc.bookstore.api.security.service.UserService;
import com.siby.assignment.hsbc.bookstore.api.security.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.badRequest;

@Controller
@RequestMapping("/registration")
public class UserRegistrationRestController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public ResponseEntity<?> registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                                 BindingResult result) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return badRequest().build();
        }

        userService.save(userDto);

        return accepted().build();
    }

}