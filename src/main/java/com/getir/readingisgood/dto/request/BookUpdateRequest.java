package com.getir.readingisgood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookUpdateRequest {
    private String bookId;
    private int stock;
}
