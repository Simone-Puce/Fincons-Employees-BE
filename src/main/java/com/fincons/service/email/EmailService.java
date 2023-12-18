package com.fincons.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private EmailHireDate emailHireDate;
    @Autowired
    private EmailBirthDate emailBirthDate;

    public void sendGreetingsToEmployeesBirth() throws RuntimeException{
        emailBirthDate.sendBirthdayGreetings();
    }

    public void sendGreetingsToEmployeesHire() throws RuntimeException{
        emailHireDate.sendAnniversaryGreetings();
    }
}
