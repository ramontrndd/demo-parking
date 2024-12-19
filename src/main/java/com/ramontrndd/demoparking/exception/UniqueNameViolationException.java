package com.ramontrndd.demoparking.exception;

public class UniqueNameViolationException extends RuntimeException {
    public UniqueNameViolationException(String message) {
        super(message);
    }
}
