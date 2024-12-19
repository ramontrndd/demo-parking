package com.ramontrndd.demoparking.exception;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException(String message) {
        super(message);
    }
}
