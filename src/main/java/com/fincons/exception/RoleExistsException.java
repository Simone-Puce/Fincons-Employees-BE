package com.fincons.exception;

public class RoleExistsException extends Exception{

    public RoleExistsException() {
    }

    public RoleExistsException(String message) {
        super(message);
    }

    public RoleExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleExistsException(Throwable cause) {
        super(cause);
    }

    public RoleExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
