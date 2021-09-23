package com.getir.readingisgood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequest {
    private String bookName;
    private String bookAuthor;
    private double price;
    private int stock;
}
