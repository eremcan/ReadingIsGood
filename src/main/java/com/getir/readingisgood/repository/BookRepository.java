package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findBookByBookName(String name);
}
