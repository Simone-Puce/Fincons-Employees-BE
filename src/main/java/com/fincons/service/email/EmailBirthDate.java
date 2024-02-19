package com.fincons.service.email;

import com.fincons.entity.Employee;
import com.fincons.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
    public static final Predicate<Employee> emailCorrectFormat = employee -> employee.getEmail().matches("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    public static final String EMAIL_SUBJECT_BIRTHDATE = "Happy Birthdate!";
    private static final String EMAIL_SUBCONTENT_BIRTHDATE = "May life gift you with radiant smiles, unexpected successes, and boundless love every day. Heartfelt wishes for a bright future filled with accomplishments and joy!";
    private static final String IMG_BIRTHDATE = "images/happyBirthday.png";
    private static final String LOGO = "images/logo.png";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSender emailSender;
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
        Map<String, Resource> inLineResource = new HashMap<>();
        Map<String, Resource> attachmentResource = new HashMap<>();
        emailContent.put("name", employee.getFirstName());
        emailContent.put("lastName", employee.getLastName());
        emailContent.put("personalizedTextSub", EMAIL_SUBCONTENT_BIRTHDATE);
        inLineResource.put("img", new ClassPathResource(IMG_BIRTHDATE));
        inLineResource.put("logo", new ClassPathResource(LOGO));
        String htmlContent = emailContentBuilder.buildEmailContent(emailContent, "email-template");
        emailSender.sendEmail(employee.getEmail(), EMAIL_SUBJECT_BIRTHDATE, htmlContent, inLineResource, attachmentResource);
        logger.info("Email sent to {}", employee.getFirstName());
    };
}