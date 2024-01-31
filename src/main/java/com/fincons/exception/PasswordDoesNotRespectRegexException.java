package com.fincons.exception;

public class PasswordDoesNotRespectRegexException extends Exception{

    public PasswordDoesNotRespectRegexException() {
        super("Password does not respect regex !");
    }

    public PasswordDoesNotRespectRegexException(String message) {
        super(message);
    }

    public PasswordDoesNotRespectRegexException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordDoesNotRespectRegexException(Throwable cause) {
        super(cause);
    }

    public PasswordDoesNotRespectRegexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
