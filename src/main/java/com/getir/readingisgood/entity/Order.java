package com.getir.readingisgood.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("orders")
public class Order {

    @Id
    private String orderId;

    @Indexed
    private String customerId;
    private String bookId;
    private int amount;

    private double price;

    LocalDateTime createdAt;


    public Order(String customerId, String bookId, Integer amount, Double price) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.amount = amount;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }
}
