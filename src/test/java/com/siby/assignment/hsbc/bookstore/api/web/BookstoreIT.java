package com.siby.assignment.hsbc.bookstore.api.web;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;

import com.siby.assignment.hsbc.bookstore.api.model.Book;
import com.siby.assignment.hsbc.bookstore.api.model.Bookstore;
import com.siby.assignment.hsbc.bookstore.api.repository.BookRepository;
import com.siby.assignment.hsbc.bookstore.api.repository.BookstoreRepository;
import com.siby.assignment.hsbc.bookstore.builders.UserRegistrationDtoBuilder;
import com.siby.assignment.hsbc.bookstore.security.web.dto.UserRegistrationDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("it")
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BookstoreIT {

    private static final String ROLE = "USER";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String bookstoreName = "enfield";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Bookstore bookstore;

    private List<Book> bookList = new ArrayList<>();

    private UserRegistrationDto userRegistrationDto;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookstoreRepository bookstoreRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();

        this.bookRepository.deleteAllInBatch();
        this.bookstoreRepository.deleteAllInBatch();

        this.bookstore = bookstoreRepository.save(new Bookstore(bookstoreName));
        this.bookList.add(bookRepository.save(new Book(bookstore, "http://books/1/" + bookstoreName, "A title")));
        this.bookList.add(bookRepository.save(new Book(bookstore, "http://books/2/" + bookstoreName, "A title")));
    }

    @Before
    public void registerUser() throws Exception {
        // given
        userRegistrationDto = UserRegistrationDtoBuilder.instance().build();
        // and
        MockHttpServletRequestBuilder registrationRequest = createRegistrationRequest(userRegistrationDto);

        this.mockMvc
                .perform(registrationRequest)
                .andExpect(redirectedUrl("/registration?success"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void bookstoreAlreadyExists() throws Exception {
        String bookstore = RandomStringUtils.randomAlphabetic(3);

        this.mockMvc.perform(post("/" + bookstore + "/register")
                .contentType(contentType)
                .with(user(userRegistrationDto.getEmail()).password(userRegistrationDto.getPassword()).roles(ROLE)))
                .andExpect(header().string("location", "http://localhost/" + bookstore + "/books"))
                .andExpect(status().isCreated());
        // and
        this.mockMvc.perform(post("/" + bookstore + "/register")
                .contentType(contentType)
                .with(user(userRegistrationDto.getEmail()).password(userRegistrationDto.getPassword()).roles(ROLE)))
                .andExpect(status().isConflict());
    }

    @Test
    public void registerBookstore() throws Exception {
        String bookstore = RandomStringUtils.randomAlphabetic(3);

        this.mockMvc.perform(post("/" + bookstore + "/register")
                .contentType(contentType)
                .with(user(userRegistrationDto.getEmail()).password(userRegistrationDto.getPassword()).roles(ROLE)))
                .andExpect(header().string("location", "http://localhost/" + bookstore + "/books"))
                .andExpect(status().isCreated());
    }

    @Test
    public void bookstoreNotFound() throws Exception {
        mockMvc.perform(get("/unknown/books")
                .with(user(userRegistrationDto.getEmail()).password(userRegistrationDto.getPassword()).roles(ROLE)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readBooks() throws Exception {
        mockMvc.perform(get("/" + bookstoreName + "/books")
                .with(user(userRegistrationDto.getEmail()).password(userRegistrationDto.getPassword()).roles(ROLE)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.bookResourceList[0].book.id", is(this.bookList.get(0).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookResourceList[0].book.author", is("http://books/1/" + bookstoreName)))
                .andExpect(jsonPath("$._embedded.bookResourceList[0].book.title", is("A title")))
                .andExpect(jsonPath("$._embedded.bookResourceList[0]._links.books.href", is("http://localhost/enfield")))
                .andExpect(jsonPath("$._embedded.bookResourceList[0]._links.self.href", is("http://localhost/enfield/books/1")))
                .andExpect(jsonPath("$._embedded.bookResourceList[1].book.id", is(this.bookList.get(1).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookResourceList[1].book.author", is("http://books/2/" + bookstoreName)))
                .andExpect(jsonPath("$._embedded.bookResourceList[1].book.title", is("A title")))
                .andExpect(jsonPath("$._embedded.bookResourceList[1]._links.books.href", is("http://localhost/enfield")))
                .andExpect(jsonPath("$._embedded.bookResourceList[1]._links.self.href", is("http://localhost/enfield/books/2")));
    }

    @Test
    public void readSingleBook() throws Exception {
        mockMvc.perform(get("/" + bookstoreName + "/books/" + this.bookList.get(0).getId())
                .with(user(userRegistrationDto.getEmail()).password(userRegistrationDto.getPassword()).roles(ROLE)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.book.id", is(this.bookList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.book.author", is("http://books/1/" + bookstoreName)))
                .andExpect(jsonPath("$.book.title", is("A title")))
                .andExpect(jsonPath("$._links.books.href", is("http://localhost/enfield")))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/enfield/books/12")));
    }

    @Test
    public void createBook() throws Exception {
        String bookJson = json(new Book(
                this.bookstore, "author", "title"));

        this.mockMvc.perform(post("/" + bookstoreName + "/books")
                .contentType(contentType)
                .content(bookJson)
                .with(user(userRegistrationDto.getEmail()).password(userRegistrationDto.getPassword()).roles(ROLE)))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
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