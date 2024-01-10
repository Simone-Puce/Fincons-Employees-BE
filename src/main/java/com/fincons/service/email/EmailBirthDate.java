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
public class EmailBirthDate implements IEmailBirthDate{
    Logger logger = LoggerFactory.getLogger(EmailBirthDate.class);
    public static final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    public static final Predicate<Employee> emailCorrectFormat = employee -> employee.getEmail().matches("^[a-zA-Z]+\\.[a-zA-Z]+@gmail\\.com$");
    public static final String EMAIL_SUBJECT_BIRTHDATE = "Happy Birthdate!";
    public static final String EMAIL_CONTENT_BIRTHDATE = "We wish you a fantastic birthday full of joy!";
    private static final String IMG_BIRTHDATE = "images/happyBirthday.png";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @Override
    public void sendBirthdayGreetings() throws RuntimeException {
        employeeRepository.findEmployeesByTodayBirthday(LocalDate.now()).stream()
                .filter(emailNotEmpty)
                .filter(emailCorrectFormat)
                .forEach(getEmployeeConsumer);
    }

    private final Consumer<Employee> getEmployeeConsumer = employee -> {
        try {
            Map<String, Object> emailContent = new HashMap<>();
            emailContent.put("name", employee.getFirstName());
            emailContent.put("lastName", employee.getLastName());
            emailContent.put("personalizedText", EMAIL_CONTENT_BIRTHDATE);
            String htmlContent = emailContentBuilder.buildEmailContent(emailContent);
            if (employee.getEmail().equals("bobbie.roberts@gmail.com")) {
                throw new RuntimeException("The email is the same and therefore an exception occurred");
            } else {
                emailSend.sendEmail(employee.getEmail(), EMAIL_SUBJECT_BIRTHDATE, htmlContent, IMG_BIRTHDATE);
                logger.info("Email sent to {}", employee.getFirstName());
            }
        } catch (RuntimeException e){
            logger.error("Error occurred while sending email: %d ", e);
        }

    };
}