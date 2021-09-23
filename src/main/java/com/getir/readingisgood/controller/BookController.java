package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.request.BookRequest;
import com.getir.readingisgood.dto.request.BookUpdateRequest;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/book", produces = "application/json")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest) {
        Book response = bookService.addBook(bookRequest);
        return ResponseEntity.ok(bookService.convertToBookDto(response));
    }

    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody BookUpdateRequest bookUpdateRequest) {
        Book response = bookService.updateBookStock(bookUpdateRequest);
        return ResponseEntity.ok(bookService.convertToBookDto(response));
    }

    @GetMapping(value = "/{Id}")
    public ResponseEntity<?> getBookById(@PathVariable("Id") String bookId) {
        Book response = bookService.getBookById(bookId);
        return ResponseEntity.ok(bookService.convertToBookDto(response));
    }


}
