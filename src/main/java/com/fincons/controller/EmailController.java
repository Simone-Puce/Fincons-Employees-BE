package com.fincons.controller;


import com.fincons.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-greetings")
    public String sendGreetingsToEmployees() {
        try {
            emailService.sendGreetingsToEmployeesBirth();
            emailService.sendGreetingsToEmployeesHire();
            return "Greetings sent successfully!";
        } catch (Exception e) {
            return "Error sending greetings: " + e.getMessage();
        }
    }
}
