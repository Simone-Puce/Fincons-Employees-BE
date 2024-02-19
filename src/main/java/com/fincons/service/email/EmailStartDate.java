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
public class EmailStartDate implements IEmailStartDate {

    Logger logger = LoggerFactory.getLogger(EmailStartDate.class);
    private final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    public static final Predicate<Employee> emailCorrectFormat = employee -> employee.getEmail().matches("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private static final String EMAIL_SUBJECT_HIREDATE = "Happy Anniversary!";
    private static final String EMAIL_CONTENT_HIREDATE = "Congratulations on joining our team!";
    private static final String IMG_HIREDATE = "images/happyAnniversary.png";
    private static final String LOGO = "images/logo.png";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @Override
    public void sendAnniversaryGreetings() throws IllegalArgumentException{
        employeeRepository.findEmployeesByTodayStartDate(LocalDate.now()).stream()
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
        emailContent.put("personalizedText", EMAIL_CONTENT_HIREDATE);
        inLineResource.put("img", new ClassPathResource(IMG_HIREDATE));
        inLineResource.put("logo", new ClassPathResource(LOGO));
        String htmlContent = emailContentBuilder.buildEmailContent(emailContent, "email-template");
        emailSender.sendEmail(employee.getEmail(), EMAIL_SUBJECT_HIREDATE, htmlContent, inLineResource, attachmentResource);
        logger.info("Email sent to {}", employee.getFirstName());
    };

}