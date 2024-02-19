package com.fincons.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailRegistrationOccurred implements IEmailRegistrationOccurred {

    Logger logger = LoggerFactory.getLogger(EmailRegistrationOccurred.class);
    public static final String EMAIL_SUBJECT_REGISTRATION_OCCURRED = "Registrazione avvenuta con successo!";
    public static final String EMAIL_CONTENT_REGISTRATION_OCCURRED = "Benvenuto, ti ringraziamo di esserti registrato sul nostro sito";
    private static final String IMG_REGISTRATION_OCCURED = "/images/benvenuto.jpg";
    private static final String LOGO = "/images/logo.png";
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @Override
    public void sendEmailRegistrationOccurred(String firstName, String lastName, String email) throws IllegalArgumentException {
        Map<String, Object> emailContent = new HashMap<>();
        Map<String, Resource> inLineResource = new HashMap<>();
        Map<String, Resource> attachmentResource = new HashMap<>();
        emailContent.put("firstName", firstName);
        emailContent.put("lastName", lastName);
        emailContent.put("personalizedTextSub", EMAIL_CONTENT_REGISTRATION_OCCURRED);
        inLineResource.put("img", new ClassPathResource(IMG_REGISTRATION_OCCURED));
        inLineResource.put("logo", new ClassPathResource(LOGO));
        String htmlContent = emailContentBuilder.buildEmailContent(emailContent, "registrationOccurred-template");
        emailSender.sendEmail(email, EMAIL_SUBJECT_REGISTRATION_OCCURRED, htmlContent, inLineResource, attachmentResource);
        logger.info("Email sent to {}", firstName);
    }
}
