package com.getir.readingisgood.integrationtests.book;

import com.getir.readingisgood.ReadingIsGoodApplication;
import com.getir.readingisgood.dto.request.BookRequest;
import com.getir.readingisgood.dto.request.BookUpdateRequest;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ReadingIsGoodApplication.class)
public class BookServiceITest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Test
    public void addBook() {

        String bookName = "Suc ve Ceza";
        Optional<Book> newBook = bookRepository.findBookByBookName(bookName);

        newBook.ifPresent(existedBook -> bookRepository.deleteById(existedBook.getBookId()));

        BookRequest newBookRequest = new BookRequest(bookName, "Dostoyevski", 10.00, 30);

        Integer expectedNumberOfBooksAfterAdd = bookRepository.findAll().size() + 1;

        bookService.addBook(newBookRequest);

        Integer actual = bookRepository.findAll().size();

        assertEquals(expectedNumberOfBooksAfterAdd, actual);


    }

    @Test
    public void testUpdateBook() {

        Book existedBook = bookRepository.findAll().get(0);

        if (existedBook == null) {
            assertThrows(
                    Exception.class,
                    () -> bookService.updateBookStock(new BookUpdateRequest("10", 10)));
        }

        assert existedBook != null;
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(existedBook.getBookId(), 20);
        Book updatedBook = bookService.updateBookStock(bookUpdateRequest);


        assertEquals(20, updatedBook.getStock());

    }


}
