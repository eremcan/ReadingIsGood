package com.getir.readingisgood.exceptions;

import com.getir.readingisgood.exceptions.generic.ReadingIsGoodException;
import org.springframework.http.HttpStatus;

public class OrderServiceException extends ReadingIsGoodException {

    public enum Exception {
        INSUFFICIENT_STOCK("get.order.insufficient.stock", HttpStatus.BAD_REQUEST),
        ORDER_INVALID_NUMBER("get.order.invalid.number", HttpStatus.BAD_REQUEST),
        ORDER_NOT_FOUND("get.order.not.found", HttpStatus.NOT_FOUND),
        CUSTOMER_HAS_NO_ORDER("get.order.customer.has.no.order", HttpStatus.NOT_FOUND),
        DATE_IN_BETWEEN_NOT_FOUND("get.order.date.in.between.not.found", HttpStatus.NOT_FOUND);


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

    public OrderServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
