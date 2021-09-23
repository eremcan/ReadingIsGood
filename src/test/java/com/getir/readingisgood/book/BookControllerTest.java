package com.getir.readingisgood.book;

import com.getir.readingisgood.controller.BookController;
import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.dto.request.BookRequest;
import com.getir.readingisgood.dto.request.BookUpdateRequest;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @Test
    public void testConstructor() {

        BookRepository bookRepository = mock(BookRepository.class);
        new BookController(new BookService(bookRepository, new ModelMapper(), null));
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setCreatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setPrice(5.0);
        book.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setBookName("Suc ve Ceza");
        book.setBookId("223345");
        book.setBookAuthor("Dostoyevski");
        book.setStock(19);
        BookRepository bookRepository = mock(BookRepository.class);

        when(bookRepository.findBookByBookName(anyString())).thenReturn(Optional.empty());
        BookController bookController = new BookController(new BookService(bookRepository, new ModelMapper(), null));
        bookController.addBook(new BookRequest("Suc ve Ceza", "Dostoyevski", 5.0, 19));
        verify(bookRepository).findBookByBookName(anyString());
    }

    @Test
    public void testGetBookById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/{bookId}", "223345");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void testUpdateBook() {
        Book book = new Book();
        LocalDateTime ofResult = LocalDateTime.of(2020, 1, 1, 10, 10);
        book.setCreatedAt(ofResult);
        book.setPrice(10.0);
        book.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setBookName("Suc ve Ceza");
        book.setBookId("223345");
        book.setBookAuthor("Dostoyevski");
        book.setStock(1);
        Optional<Book> ofResult1 = Optional.<Book>of(book);

        Book book1 = new Book();
        book1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book1.setPrice(10.0);
        book1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book1.setBookName("Suc ve Ceza");
        book1.setBookId("223345");
        book1.setBookAuthor("Dostoyevski");
        book1.setStock(1);
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.save(any())).thenReturn(book1);
        when(bookRepository.findById(anyString())).thenReturn(ofResult1);
        BookController bookController = new BookController(new BookService(bookRepository, new ModelMapper(), null));
        ResponseEntity<?> actualUpdateBookResult = bookController.updateBook(new BookUpdateRequest("223345", 10));
        assertTrue(actualUpdateBookResult.hasBody());
        assertTrue(actualUpdateBookResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateBookResult.getStatusCode());
        assertEquals(10.0, ((BookDto) actualUpdateBookResult.getBody()).getPrice());
        assertEquals("223345", ((BookDto) actualUpdateBookResult.getBody()).getId());
        assertSame(ofResult, ((BookDto) actualUpdateBookResult.getBody()).getCreatedAt());
        assertEquals("Suc ve Ceza", ((BookDto) actualUpdateBookResult.getBody()).getBookName());
        assertEquals("Dostoyevski", ((BookDto) actualUpdateBookResult.getBody()).getBookAuthor());
        assertEquals(10, ((BookDto) actualUpdateBookResult.getBody()).getStock());
        verify(bookRepository).findById(anyString());
        verify(bookRepository).save(any());
    }

    @Test
    public void testAddBookWhenReturnException() {
        Book book = new Book();
        book.setCreatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setPrice(10.0);
        book.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setBookName("Suc ve Ceza");
        book.setBookId("223345");
        book.setBookAuthor("Dostoyevski");
        book.setStock(5);
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findBookByBookName(anyString())).thenReturn(Optional.of(book));
        BookController bookController = new BookController(new BookService(bookRepository, new ModelMapper(), null));
        assertThrows(Exception.class,
                () -> bookController.addBook(new BookRequest("Suc ve Ceza", "Dostoyevski.", 10.0, 5)));
    }


}

