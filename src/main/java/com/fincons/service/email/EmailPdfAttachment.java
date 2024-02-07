package com.fincons.service.email;

import com.fincons.entity.Employee;
import com.fincons.repository.EmployeeRepository;
import jakarta.mail.internet.MimeBodyPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class EmailPdfAttachment implements IEmailPdfAttachment {

    Logger logger = LoggerFactory.getLogger(EmailPdfAttachment.class);
    public static final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    public static final Predicate<Employee> emailCorrectFormat = employee -> employee.getEmail().matches("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

    public static final String EMAIL_SUBJECT_PDF = "Certificazioni consegiute";
    public static final String EMAIL_CONTENT_PDF = "Nel PDF in allegato è riportato l'elenco con i dipendenti che hanno conseguito una certificazione il mese precedente";
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EmailContentBuilder emailContentBuilder;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public void sendPdfAttachment() throws IllegalArgumentException {
        employeeRepository.findAll().stream()
                .filter(emailNotEmpty)
                .filter(emailCorrectFormat)
                .forEach(getEmployeeEmailConsumer);

    }

    private final Consumer<Employee> getEmployeeEmailConsumer = employee -> {
        Map<String, Object> emailContent = new HashMap<>();
        emailContent.put("personalizedTextSub", EMAIL_CONTENT_PDF);
        String htmlContent = emailContentBuilder.buildEmailContent(emailContent, "certifications-pdf-template");
        try {
            emailSender.sendPdf(employee.getEmail(), EMAIL_SUBJECT_PDF, htmlContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Email sent to {}", employeeRepository.findAll());
    };


}
