package com.fincons.exception;

public class EmailDoesNotExistException extends Exception{

    public EmailDoesNotExistException() {
        super("Email does not exist!");
    }

    public EmailDoesNotExistException(String message) {
        super(message);
    }

    public EmailDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public EmailDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
