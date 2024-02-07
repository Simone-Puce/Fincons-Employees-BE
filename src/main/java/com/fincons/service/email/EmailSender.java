package com.fincons.service.email;

import com.fincons.controller.CertificateEmployeeController;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


@Service
public class EmailSender {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private CertificateEmployeeController certificateEmployeeController;

    public void sendEmail(String to, String subject, String htmlContent, String imagePath) throws IllegalArgumentException {
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
            throw new IllegalArgumentException("Error sending email to " + to, e);
        }
    }

    public void sendPdf(String to, String subject, String htmlContent) throws IllegalArgumentException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        ClassPathResource logo = new ClassPathResource("images/logo.png");
        //File file = certificateEmployeeController.generateFilePdf(LocalDate.of(2000,1,1), LocalDate.of(2023,12,31));
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.addInline("logo", logo);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Error sending email to " + to, e);
        }
    }
}