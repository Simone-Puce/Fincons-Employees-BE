package com.fincons.exception;

public class RoleDoesNotRespectRegex extends Exception{
    public RoleDoesNotRespectRegex() {
    }

    public RoleDoesNotRespectRegex(String message) {
        super(message);
    }

    public RoleDoesNotRespectRegex(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleDoesNotRespectRegex(Throwable cause) {
        super(cause);
    }

    public RoleDoesNotRespectRegex(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
