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
public class EmailBirthDate {
    public static final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    public static final String EMAIL_SUBJECT_BIRTHDATE = "Buon Compleanno!";
    public static final String EMAIL_CONTENT_BIRTHDATE = "Ti auguriamo un fantastico compleanno pieno di gioia!";
    private static final String IMG_BIRTHDATE = "images/happyBirthday.png";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private EmailContentBuilder emailContentBuilder;

    public void sendBirthdayGreetings() throws RuntimeException {
        employeeRepository.findEmployeesByTodayBirthday(LocalDate.now()).stream()
                .filter(emailNotEmpty)
                .forEach(getEmployeeConsumer());
    }

    private Consumer<Employee> getEmployeeConsumer() {
        return employee -> {
            Map<String, Object> emailContent = new HashMap<>();
            emailContent.put("name", employee.getFirstName());
            emailContent.put("lastName", employee.getLastName());
            emailContent.put("personalizedText", EMAIL_CONTENT_BIRTHDATE);
            String htmlContent = emailContentBuilder.buildEmailContent(emailContent);
            emailSend.sendEmail(employee.getEmail(), EMAIL_SUBJECT_BIRTHDATE, htmlContent, IMG_BIRTHDATE);
        };
    }
}