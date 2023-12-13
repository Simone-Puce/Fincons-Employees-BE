package com.fincons.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailSend {

    @Autowired
    private JavaMailSender emailSender;


    public void sendEmail(String to, String subject, String htmlContent, String imagePath) throws RuntimeException {
        MimeMessage message = emailSender.createMimeMessage();
        ClassPathResource file = new ClassPathResource("images/logo.png");
        ClassPathResource image = new ClassPathResource(imagePath);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.addInline("logo", file);
            helper.addInline("img", image);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Errore nell'invio dell'email: " + e.getMessage());
        }
    }
}
