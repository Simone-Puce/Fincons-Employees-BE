package com.fincons.controller;

import com.fincons.jobscheduler.JobScheduler;
import com.fincons.service.email.IEmailRegistrationOccurred;
import com.fincons.service.employeeService.ICreateRandomCertificateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/company-employee-management")
public class EmailController {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private IEmailRegistrationOccurred iEmailRegistrationOccurred;

    @PostMapping("${email.sender.birth}")
    public ResponseEntity<String> emailSenderBirth() {
        jobScheduler.emailSenderBirth();
        return ResponseEntity.ok("Birthday emails have been sent successfully");
    }

    @PostMapping("${email.sender.hire}")
    public ResponseEntity<String> emailSenderHire() {
        jobScheduler.emailSenderHire();
        return ResponseEntity.ok("Anniversary emails have been sent successfully");
    }
    @PostMapping("${email.sender.pdf}")
    public ResponseEntity<String> emailSenderPdf() throws IOException {
        jobScheduler.emailSenderPdf();
        return ResponseEntity.ok("Pdf emails have been sent successfully");
    }

    @PostMapping("${new.employee.random}")
    public ResponseEntity<String> newEmployeeRandom() {
        jobScheduler.newEmployeeRandom();
        return ResponseEntity.ok("New employees have been created successfully");
    }

    @PostMapping("/email-sender-registration-occurred")
    public ResponseEntity<String> emailSenderRegistrationOccurred(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email) {
        iEmailRegistrationOccurred.sendEmailRegistrationOccurred(firstName, lastName, email);
        return ResponseEntity.ok("The registration email has been sent successfully");
    }
}