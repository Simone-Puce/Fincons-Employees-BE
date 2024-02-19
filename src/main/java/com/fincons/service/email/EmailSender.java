package com.fincons.service.email;

import com.fincons.controller.CertificateEmployeeController;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;


@Service
public class EmailSender {
    Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String htmlContent, Map<String, Resource> inLineResource, Map<String, Resource> attachmentResource) throws IllegalArgumentException {
        MimeMessage message = emailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            for (Map.Entry<String, Resource> entry : inLineResource.entrySet()){
                helper.addInline(entry.getKey(), entry.getValue());
                logger.info(entry.getKey());
                logger.info(String.valueOf(entry.getValue()));
            }
            for(Map.Entry<String, Resource> entry : attachmentResource.entrySet()){
                helper.addAttachment(entry.getKey(), entry.getValue());
            }
            emailSender.send(message);
        }catch (MessagingException e){
            throw new IllegalArgumentException("Error sending email to " + to, e);
        }
    }



    /*public void sendeEmail(String to, String subject, String htmlContent, String imagePath) throws IllegalArgumentException {
        MimeMessage message = emailSender.createMimeMessage();
        ClassPathResource logo = new ClassPathResource("images/logo.png");
        ClassPathResource image = new ClassPathResource(imagePath);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.addInline("logo", logo);
            helper.addInline("img", image);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Error sending email to " + to, e);
        }
    }

    public void sendPdf(String to, String subject, String htmlContent, byte[] pdf) throws IllegalArgumentException {
        MimeMessage message = emailSender.createMimeMessage();
        ClassPathResource logo = new ClassPathResource("images/logo.png");
        ByteArrayResource byteArrayResource = new ByteArrayResource(pdf);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.addInline("logo", logo);
            helper.addAttachment("file.pdf", byteArrayResource);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Error sending email to " + to, e);
        }
    }*/
}