//package com.siby.assignment.hsbc.bookstore.api.rest;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//import java.util.Collection;
//import java.util.Optional;
//import java.util.Random;
//
//import com.siby.assignment.hsbc.bookstore.api.domain.Book;
//import com.siby.assignment.hsbc.bookstore.api.domain.Bookstore;
//import com.siby.assignment.hsbc.bookstore.api.repository.BookRepository;
//import com.siby.assignment.hsbc.bookstore.api.repository.BookstoreRepository;
//import com.siby.assignment.hsbc.bookstore.api.rest.exception.BookStoreNotFoundException;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.assertj.core.util.Lists;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest
//public class BookstoreRestControllerTest {
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private BookstoreRepository bookstoreRepository;
//
//
//    private BookstoreRestController bookstoreRestController;
//
//    @Before
//    public void init() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
//        RequestContextHolder.setRequestAttributes(attributes);
//    }
//
//    @After
//    public void cleanUp() {
//        RequestContextHolder.resetRequestAttributes();
//    }
//
//    @Before
//    public void setUp() {
//        bookstoreRestController = new BookstoreRestController(bookRepository, bookstoreRepository);
//    }
//
//    @Test
//    public void shouldThrowBookstoreNotFoundException() {
//
//        // given
//        String bookstoreName = randomString();
//        // and
//        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.empty());
//
//        // expect
//        thrown.expect(BookStoreNotFoundException.class);
//
//        // when
//        bookstoreRestController.validateBookstore(bookstoreName);
//    }
//
//    @Test
//    public void shouldReadBooks() {
//        // given
//        String bookstoreName = randomString();
//        // and
//        String password = randomString();
//        Bookstore bookstore = new Bookstore(bookstoreName, password);
//        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.of(bookstore));
//        // and
//        String author = randomString();
//        String title = randomString();
//        Collection<Book> expectedBooks = Lists.newArrayList(new Book(bookstore, author, title));
//        // and
//        given(bookRepository.findByBookstoreName(bookstoreName)).willReturn(expectedBooks);
//
//        // when
//        Collection<Book> books = bookstoreRestController.readBooks(bookstoreName);
//
//        // then
//        assertThat(books, is(expectedBooks));
//    }
//
//    @Test
//    public void shouldReadBook() {
//
//        // given
//        String bookstoreName = randomString();
//        // and
//        String password = randomString();
//        Bookstore bookstore = new Bookstore(bookstoreName, password);
//        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.of(bookstore));
//        // and
//        Long bookId = randomLong();
//        String author = randomString();
//        String title = randomString();
//        Book expectedBook = new Book(bookstore, author, title);
//        // and
//        given(bookRepository.findOne(bookId)).willReturn(expectedBook);
//
//
//        // when
//        Book book = bookstoreRestController.readBook(bookstoreName, bookId);
//
//        // then
//        assertThat(book, is(expectedBook));
//    }
//
//    @Ignore //TODO
//    public void shouldAddBook() {
//        // given
//        String bookstoreName = randomString();
//        // and
//        String password = randomString();
//        Bookstore bookstore = new Bookstore(bookstoreName, password);
//        given(bookstoreRepository.findByName(bookstoreName)).willReturn(Optional.of(bookstore));
//        // and
//        Long bookId = randomLong();
//        String author = randomString();
//        String title = randomString();
//        Book book = new Book(bookstore, author, title);
//        // and
//
//
//        // when
//        bookstoreRestController.add(bookstoreName, book);
//
//        ArgumentCaptor<Book> argument = ArgumentCaptor.forClass(Book.class);
//        verify(bookRepository).save(argument.capture());
//        assertThat(argument.getValue().getAuthor(), is(author));
//    }
//
//    private Long randomLong() {
//        return new Random().nextLong();
//    }
//
//    private String randomString() {
//        return RandomStringUtils.random(3);
//    }
//}