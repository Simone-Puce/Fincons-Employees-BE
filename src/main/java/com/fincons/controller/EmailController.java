package com.fincons.controller;

import com.fincons.jobscheduler.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${email.uri}")
public class EmailController {

    @Autowired
    private JobScheduler jobScheduler;

    @PostMapping("/emailSenderBirth")
    public ResponseEntity<String> emailSenderBirth() {
        jobScheduler.emailSenderBirth();
        return ResponseEntity.ok("Birthday emails have been sent successfully");
    }

    @PostMapping("/emailSenderHire")
    public ResponseEntity<String> emailSenderHire() {
        jobScheduler.emailSenderHire();
        return ResponseEntity.ok("Anniversary emails have been sent successfully");
    }

    @PostMapping("/newEmployeeRandom")
    public ResponseEntity<String> newEmployeeRandom() {
        jobScheduler.newEmployeeRandom();
        return ResponseEntity.ok("New employees have been created successfully");
    }
}