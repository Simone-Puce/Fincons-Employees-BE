package com.fincons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeIfNotException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmployeeIfNotException(String message){
        super (message);
    }
}
