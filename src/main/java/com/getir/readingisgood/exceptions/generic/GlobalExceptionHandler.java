package com.getir.readingisgood.exceptions.generic;

import com.getir.readingisgood.exceptions.BookServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BookServiceException.class)
    public ResponseEntity<ErrorResponseModel> genericBookServiceException(BookServiceException bookServiceException) {
        log.error(bookServiceException.getLocalizedMessage(), bookServiceException);
        return new ResponseEntity<>(toExceptionModel(bookServiceException), bookServiceException.getHttpStatus());
    }

    private ErrorResponseModel toExceptionModel(ReadingIsGoodException exception) {
        ErrorResponseModel.ErrorResponseModelBuilder builder = ErrorResponseModel.builder()
                .error(exception.getHttpStatus().name())
                .message(exception.getMessage())
                .status(exception.getHttpStatus().toString())
                .timestamp(LocalDateTime.now().toString());

        return builder.build();
    }


}
