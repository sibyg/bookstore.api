package com.siby.assignment.hsbc.bookstore.api.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.siby.assignment.hsbc.bookstore.api.Application;
import com.siby.assignment.hsbc.bookstore.api.domain.Book;
import com.siby.assignment.hsbc.bookstore.api.domain.Bookstore;
import com.siby.assignment.hsbc.bookstore.api.repository.BookRepository;
import com.siby.assignment.hsbc.bookstore.api.repository.BookstoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BookRestControllerAcceptanceTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String bookstoreName = "enfield";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Bookstore bookstore;

    private List<Book> bookList = new ArrayList<>();

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

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookRepository.deleteAllInBatch();
        this.bookstoreRepository.deleteAllInBatch();

        this.bookstore = bookstoreRepository.save(new Bookstore(bookstoreName, "password"));
        this.bookList.add(bookRepository.save(new Book(bookstore, "http://books/1/" + bookstoreName, "A title")));
        this.bookList.add(bookRepository.save(new Book(bookstore, "http://books/2/" + bookstoreName, "A title")));
    }

    @Test
    public void bookstoreNotFound() throws Exception {
        mockMvc.perform(post("/george/bookmarks/")
                .content(this.json(new Book()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readBooks() throws Exception {
        mockMvc.perform(get("/" + bookstoreName + "/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.bookList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].author", is("http://books/1/" + bookstoreName)))
                .andExpect(jsonPath("$[0].title", is("A title")))
                .andExpect(jsonPath("$[1].id", is(this.bookList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].author", is("http://books/2/" + bookstoreName)))
                .andExpect(jsonPath("$[1].title", is("A title")));
    }

    @Test
    public void readSingleBook() throws Exception {
        mockMvc.perform(get("/" + bookstoreName + "/books/"
                + this.bookList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.bookList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.author", is("http://books/1/" + bookstoreName)))
                .andExpect(jsonPath("$.title", is("A title")));
    }

    @Test
    public void createBook() throws Exception {
        String bookJson = json(new Book(
                this.bookstore, "author", "title"));

        this.mockMvc.perform(post("/" + bookstoreName + "/books")
                .contentType(contentType)
                .content(bookJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}