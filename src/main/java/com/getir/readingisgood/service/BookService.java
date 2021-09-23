package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.dto.request.BookRequest;
import com.getir.readingisgood.dto.request.BookUpdateRequest;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.exceptions.BookServiceException;
import com.getir.readingisgood.exceptions.generic.ReadingIsGoodException;
import com.getir.readingisgood.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper, MessageSource messageSource) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    public Book getBookById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> buildException(BookServiceException.Exception.BOOK_NOT_FOUND, bookId));
    }

    @Transactional
    public Book addBook(BookRequest bookRequest) {
        Optional<Book> book = bookRepository.findBookByBookName(bookRequest.getBookName());

        if (book.isEmpty()) {
            Book newBook = new Book(bookRequest.getBookName(), bookRequest.getBookAuthor(), bookRequest.getStock(), bookRequest.getPrice());
            bookRepository.save(newBook);
            return newBook;
        } else {
            throw buildException(BookServiceException.Exception.BOOK_ALREADY_EXIST);
        }
    }

    @Transactional
    public Book updateBookStock(BookUpdateRequest bookUpdateRequest) {
        Book book = bookRepository.findById(bookUpdateRequest.getBookId())
                .orElseThrow(
                        () -> buildException(BookServiceException.Exception.BOOK_NOT_FOUND, bookUpdateRequest.getBookId()));

        if (bookUpdateRequest.getStock() < 0) {
            throw buildException(BookServiceException.Exception.STOCK_LOWER_ZERO);
        }

        book.setStock(bookUpdateRequest.getStock());
        book.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(book);
        return book;

    }

    @Transactional
    public Book updateBookStockForOrder(BookUpdateRequest bookUpdateRequest) {
        Book book = bookRepository.findById(bookUpdateRequest.getBookId())
                .orElseThrow(
                        () -> buildException(BookServiceException.Exception.BOOK_NOT_FOUND, bookUpdateRequest.getBookId()));

        if (book.getStock() + bookUpdateRequest.getStock() < 0) {
            throw buildException(BookServiceException.Exception.STOCK_LOWER_ZERO);
        }

        book.setStock(book.getStock() + bookUpdateRequest.getStock());
        book.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(book);
        return book;

    }

    public BookDto convertToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    private ReadingIsGoodException buildException(BookServiceException.Exception exception, Object... params) {
        return new BookServiceException(messageSource.getMessage(exception.getMessage(), params, LocaleContextHolder.getLocale()), exception.getHttpStatus());
    }
}
