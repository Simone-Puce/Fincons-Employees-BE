package com.fincons.exception;

public class PasswordException extends Exception{

    public PasswordException(String s) {
    }


    public static String  passwordDoesNotRespectRegexException() {
        return "Password does not respect regex !";
    }

    public static String  invalidPasswordException() {
        return "Invalid Password!";
    }

}
