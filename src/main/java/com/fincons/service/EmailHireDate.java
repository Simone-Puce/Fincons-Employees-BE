package com.fincons.service;


import com.fincons.entity.Employee;
import com.fincons.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class EmailHireDate {
    public static final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    public static final String EMAIL_SUBJECT_HIREDATE = "Buon Anniversario!";
    public static final String EMAIL_CONTENT_HIREDATE = "Congratulazioni per esserti unito al nostro team!";
    public static final String IMG_HIREDATE = "images/happyAnniversary.png";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private EmailContentBuilder emailContentBuilder;

    public void sendAnniversaryGreetings() throws RuntimeException{
       employeeRepository.findEmployeesByTodayHireDate(LocalDate.now()).stream()
                .filter(emailNotEmpty)
                .forEach(getEmployeeConsumer());
    }

    private Consumer<Employee> getEmployeeConsumer() {
        return employee -> {
            Map<String, Object> emailContent = new HashMap<>();
            emailContent.put("name", employee.getFirstName());
            emailContent.put("lastName", employee.getLastName());
            emailContent.put("personalizedText", EMAIL_CONTENT_HIREDATE);
            String htmlContent = emailContentBuilder.buildEmailContent(emailContent);
            emailSend.sendEmail(employee.getEmail(), EMAIL_SUBJECT_HIREDATE, htmlContent, IMG_HIREDATE);
        };
    }
}