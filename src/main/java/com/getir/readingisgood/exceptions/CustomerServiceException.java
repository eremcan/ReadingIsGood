package com.getir.readingisgood.exceptions;

import com.getir.readingisgood.exceptions.generic.ReadingIsGoodException;
import org.springframework.http.HttpStatus;

public class CustomerServiceException extends ReadingIsGoodException {
    public CustomerServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }


    public enum Exception {
        CUSTOMER_ALREADY_EXIST("create.customer.already.exist", HttpStatus.BAD_REQUEST);

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
}
