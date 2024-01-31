package com.fincons.service.email;

public interface IEmailRegistrationOccurred {
    void sendEmailRegistrationOccurred(String firstName, String lastName, String email) throws IllegalArgumentException;
}
