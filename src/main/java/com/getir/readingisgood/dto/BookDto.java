package com.getir.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String id;
    private String bookName;
    private String bookAuthor;
    private double price;
    private int stock;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
