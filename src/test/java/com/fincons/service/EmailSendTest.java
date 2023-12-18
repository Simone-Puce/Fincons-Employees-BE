package com.fincons.service;

import com.fincons.service.email.EmailSend;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EmailSendTest {

    @Autowired
    private EmailSend emailSend;

    @Test
    @Disabled
    void sendEmailOk() {
        emailSend.sendEmail("carlovittosc@gmail.com", "test1", "<html><body><h1>Title</h1></body></html>", "images/torta.png");
        assert true;
    }
    @Test
    void sendEmailKo() {
        assertThrows( RuntimeException.class, () -> emailSend.sendEmail("carlo@vittosc@gmail.com", "test1", "<html><body><h1>Title</h1></body></html>", "images/torta.png"));
    }
}