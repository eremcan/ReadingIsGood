package com.getir.readingisgood.exceptions;

import com.getir.readingisgood.exceptions.generic.ReadingIsGoodException;
import org.springframework.http.HttpStatus;

public class BookServiceException extends ReadingIsGoodException {
    public enum Exception {
        BOOK_NOT_FOUND("update.book.not.found", HttpStatus.NOT_FOUND),
        BOOK_ALREADY_EXIST("add.book.already.exist", HttpStatus.CONFLICT),
        STOCK_LOWER_ZERO("update.book.minimum.stock", HttpStatus.BAD_REQUEST);


        private final String message;
        private final HttpStatus httpStatus;

        Exception(String message, HttpStatus httpStatus) {
            this.message = message;
            this.httpStatus = httpStatus;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }

    public BookServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
