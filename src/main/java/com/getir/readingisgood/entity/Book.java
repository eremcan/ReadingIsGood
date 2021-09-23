package com.getir.readingisgood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("books")
public class Book {

    @Id
    private String bookId;

    private String bookAuthor;
    private String bookName;
    private int stock;
    private double price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Book(String bookName, String bookAuthor, Integer stock, Double price) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.stock = stock;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
