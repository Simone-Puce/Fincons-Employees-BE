package com.fincons.service;

import com.fincons.service.email.EmailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EmailSenderTest {

    @Autowired
    private EmailSender emailSender;

    /*@Test
    void sendEmailOk() {
        emailSender.sendEmail("test.test@gmail.com", "test1", "<html><body><h1>Title</h1></body></html>", "images/happyBirthday.png");
        assert true;
    }
    @Test
    void sendEmailKo() {
        assertThrows( RuntimeException.class, () -> emailSender.sendEmail("carlo@vittosc@gmail.com", "test1", "<html><body><h1>Title</h1></body></html>", "images/torta.png"));
    }*/
}