package com.fincons.service.email;

import com.fincons.entity.Employee;
import com.fincons.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class EmailBirthDate implements IEmailBirthDate {
    Logger logger = LoggerFactory.getLogger(EmailBirthDate.class);
    public static final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    public static final Predicate<Employee> emailCorrectFormat = employee -> employee.getEmail().matches("^[a-zA-Z]+\\.[a-zA-Z]+@gmail\\.com$");
    public static final String EMAIL_SUBJECT_BIRTHDATE = "Happy Birthdate!";
    public static final String EMAIL_CONTENT_BIRTHDATE = "We wish you a fantastic birthday full of joy!";
    private static final String EMAIL_SUBCONTENT_HIREDATE = "You are a shining example of how hard work can lead to meaningful results. We would like to wish you a happy anniversary in our company!";
    private static final String IMG_BIRTHDATE = "images/happyBirthday.png";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @Override
    public void sendBirthdayGreetings() throws IllegalArgumentException {
        employeeRepository.findEmployeesByTodayBirthday(LocalDate.now()).stream()
                .filter(emailNotEmpty)
                .filter(emailCorrectFormat)
                .forEach(getEmployeeConsumer);
    }

    private final Consumer<Employee> getEmployeeConsumer = employee -> {
        Map<String, Object> emailContent = new HashMap<>();
        emailContent.put("name", employee.getFirstName());
        emailContent.put("lastName", employee.getLastName());
        emailContent.put("personalizedText", EMAIL_CONTENT_BIRTHDATE);
        emailContent.put("personalizedTextSub", EMAIL_SUBCONTENT_HIREDATE);
        String htmlContent = emailContentBuilder.buildEmailContent(emailContent);
        emailSend.sendEmail(employee.getEmail(), EMAIL_SUBJECT_BIRTHDATE, htmlContent, IMG_BIRTHDATE);
        logger.info("Email sent to {}", employee.getFirstName());
    };
}