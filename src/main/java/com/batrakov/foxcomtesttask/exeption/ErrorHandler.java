package com.batrakov.foxcomtesttask.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiError> handlerNoSuchElement(NoSuchElementException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ApiError.builder()
                                           .message(ex.getLocalizedMessage())
                                           .reason("The required object was not found: " +
                                                   request.getDescription(false))
                                           .status(HttpStatus.NOT_FOUND)
                                           .build());
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiError> handlerBadValidation(ValidationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(ApiError.builder()
                                           .message(ex.getLocalizedMessage())
                                           .reason("Integrity constraint has been violated: " +
                                                   request.getDescription(false))
                                           .status(HttpStatus.CONFLICT)
                                           .build());
    }
}
