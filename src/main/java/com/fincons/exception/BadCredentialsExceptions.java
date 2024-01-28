package com.fincons.exception;

public class BadCredentialsExceptions extends Exception{
    public BadCredentialsExceptions(){
        super("Your credentials are not corrects.");
    }
}
