package com.getir.readingisgood.exceptions.generic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseModel {
    private String status;
    private String timestamp;
    private String error;
    private String message;
}
