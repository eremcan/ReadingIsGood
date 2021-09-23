package com.getir.readingisgood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {

    private String customerId;
    private String bookId;
    private int amount;

}
