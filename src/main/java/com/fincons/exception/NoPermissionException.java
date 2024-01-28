package com.fincons.exception;

public class NoPermissionException extends Exception{
    public NoPermissionException(){
        super("User does not have permission to modify the role.");
    }
}
