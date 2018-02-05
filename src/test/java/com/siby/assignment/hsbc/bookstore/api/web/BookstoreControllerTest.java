package com.siby.assignment.hsbc.bookstore.api.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import com.siby.assignment.hsbc.bookstore.api.model.Book;
import com.siby.assignment.hsbc.bookstore.api.model.Bookstore;
import com.siby.assignment.hsbc.bookstore.api.repository.BookRepository;
import com.siby.assignment.hsbc.bookstore.api.repository.BookstoreRepository;
import com.siby.assignment.hsbc.bookstore.api.web.exception.BookStoreNotFoundException;
import com.siby.assignment.hsbc.bookstore.api.web.resource.BookResource;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class BookstoreControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookstoreRepository bookstoreRepository;


    private BookstoreController bookstoreController;

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Before
    public void setUp() {
        bookstoreController = new BookstoreController(bookRepository, bookstoreRepository);
    }

    @Test
    public void shouldThrowBookstoreNotFoundException() {

        // given
        String bookstoreName = randomString();
        // and
        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.empty());

        // expect
        thrown.expect(BookStoreNotFoundException.class);

        // when
        bookstoreController.validateBookstore(bookstoreName);
    }

    @Test
    public void shouldReadBooks() {
        // given
        String bookstoreName = randomString();
        // and
        String password = randomString();
        Bookstore bookstore = new Bookstore(bookstoreName);
        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.of(bookstore));
        // and
        String author = randomString();
        String title = randomString();
        Collection<Book> expectedBooks = Lists.newArrayList(new Book(bookstore, author, title));
        // and
        given(bookRepository.findByBookstoreName(bookstoreName)).willReturn(expectedBooks);

        // when
        Resources<BookResource> books = bookstoreController.readBooks(bookstoreName);

        // then
        assertThat(books.iterator().next().getBook(), is(expectedBooks.iterator().next()));
    }

    @Test
    public void shouldReadBook() {

        // given
        String bookstoreName = randomString();
        // and
        String password = randomString();
        Bookstore bookstore = new Bookstore(bookstoreName);
        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.of(bookstore));
        // and
        Long bookId = randomLong();
        String author = randomString();
        String title = randomString();
        Book expectedBook = new Book(bookstore, author, title);
        // and
        given(bookRepository.findOne(bookId)).willReturn(expectedBook);


        // when
        BookResource bookResource = bookstoreController.readBook(bookstoreName, bookId);

        // then
        assertThat(bookResource.getBook(), is(expectedBook));
    }

    @Ignore //TODO
    public void shouldAddBook() {
        // given
        String bookstoreName = randomString();
        // and
        String password = randomString();
        Bookstore bookstore = new Bookstore(bookstoreName);
        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.of(bookstore));
        // and
        Long bookId = randomLong();
        String author = randomString();
        String title = randomString();
        Book book = new Book(bookstore, author, title);
        // and


        // when
        bookstoreController.add(bookstoreName, book);

        ArgumentCaptor<Book> argument = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(argument.capture());
        assertThat(argument.getValue().getAuthor(), is(author));
    }

    private Long randomLong() {
        return new Random().nextLong();
    }

    private String randomString() {
        return RandomStringUtils.random(3);
    }
}