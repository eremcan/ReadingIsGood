package com.getir.readingisgood.book;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ModelMapper.class, BookService.class})
@ExtendWith(SpringExtension.class)
public class BookServiceTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setCreatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setPrice(10.0);
        book.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setBookName("Suc ve Ceza");
        book.setBookId("42");
        book.setBookAuthor("Dostoyevski");
        book.setStock(1);
        when(this.bookRepository.findBookByBookName(anyString())).thenReturn(Optional.empty());
        this.bookService.addBook(new BookRequest("Suc ve Ceza", "Dostoyevski", 10.0, 1));
        verify(this.bookRepository).findBookByBookName(anyString());
    }


    @Test
    public void testUpdateBookStock() {
        Book book = new Book();
        book.setCreatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setPrice(8.0);
        book.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setBookName("Suc ve Ceza");
        book.setBookId("223355");
        book.setBookAuthor("Dostoyevski");
        book.setStock(1);
        Optional<Book> ofResult = Optional.<Book>of(book);

        Book book1 = new Book();
        book1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book1.setPrice(10.0);
        book1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book1.setBookName("Suc ve Ceza");
        book1.setBookId("223355");
        book1.setBookAuthor("Dostoyevski");
        book1.setStock(1);
        when(this.bookRepository.save((Book) any())).thenReturn(book1);
        when(this.bookRepository.findById(anyString())).thenReturn(ofResult);
        Book actualUpdateBookStockResult = this.bookService.updateBookStock(new BookUpdateRequest("42", 8));
        assertSame(book, actualUpdateBookStockResult);
        assertEquals(8.0, actualUpdateBookStockResult.getStock());
        verify(this.bookRepository).findById(anyString());
        verify(this.bookRepository).save((Book) any());
    }

    @Test
    public void testUpdateBookStockWhenStockIsSmallerThanZero() {
        Book book = new Book();
        book.setCreatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setPrice(5.5);
        book.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 10, 10));
        book.setBookName("Suc ve Ceza");
        book.setBookId("223355");
        book.setBookAuthor("Dostoyevski");
        book.setStock(-400);
        Optional<Book> ofResult = Optional.of(book);

        when(this.bookRepository.save(any())).thenReturn(ofResult.get());
        when(this.bookRepository.findById(anyString())).thenReturn(ofResult);
        assertThrows(Exception.class,
                () -> bookService.updateBookStock(new BookUpdateRequest("223355", book.getStock())));

    }


    @Test
    public void testGetBookByIdWhenBookIdIsNull() {
        when(this.bookRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> bookService.getBookById("223355"));
    }


    @Test
    public void testConvertToBookDto() {
        BookDto bookDto = new BookDto();
        when(this.modelMapper.map( any(), any())).thenReturn(bookDto);
        assertSame(bookDto, this.bookService.convertToBookDto(new Book("Suc ve Ceza", "Dostoyevski", 4, 6.0)));
        verify(this.modelMapper).map( any(), any());
    }
}

