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
public class EmailHireDate implements IEmailHireDate {

    Logger logger = LoggerFactory.getLogger(EmailHireDate.class);
    private final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    private final Predicate<Employee> emailCorrectFormat = employee -> employee.getEmail().matches("^[a-zA-Z]+\\.[a-zA-Z]+@gmail\\.com$");
    private static final String EMAIL_SUBJECT_HIREDATE = "Happy Anniversary!";
    private static final String EMAIL_CONTENT_HIREDATE = "Congratulations on joining our team!";
    private static final String IMG_HIREDATE = "images/happyAnniversary.png";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @Override
    public void sendAnniversaryGreetings() throws IllegalArgumentException{
        employeeRepository.findEmployeesByTodayHireDate(LocalDate.now()).stream()
                .filter(emailNotEmpty)
                .filter(emailCorrectFormat)
                .forEach(getEmployeeConsumer);
    }

    private final Consumer<Employee> getEmployeeConsumer = employee -> {
        Map<String, Object> emailContent = new HashMap<>();
        emailContent.put("name", employee.getFirstName());
        emailContent.put("lastName", employee.getLastName());
        emailContent.put("personalizedText", EMAIL_CONTENT_HIREDATE);
        String htmlContent = emailContentBuilder.buildEmailContent(emailContent);
        emailSend.sendEmail(employee.getEmail(), EMAIL_SUBJECT_HIREDATE, htmlContent, IMG_HIREDATE);
        logger.info("Email sent to {}", employee.getFirstName());
    };

}