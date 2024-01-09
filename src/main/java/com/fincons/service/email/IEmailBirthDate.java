package com.fincons.service.email;

import com.fincons.exception.PersonalException;

public interface IEmailBirthDate {
    void sendBirthdayGreetings() throws PersonalException;
}
