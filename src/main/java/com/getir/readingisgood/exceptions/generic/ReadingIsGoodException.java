package com.getir.readingisgood.exceptions.generic;

import org.springframework.http.HttpStatus;

public abstract class ReadingIsGoodException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ReadingIsGoodException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
