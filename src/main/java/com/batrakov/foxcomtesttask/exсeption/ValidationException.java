package com.batrakov.foxcomtesttask.exсeption;

public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
        super(message);
    }
}
