package com.fincons.jobscheduler;

import com.fincons.service.email.IEmailBirthDate;
import com.fincons.service.email.IEmailHireDate;
import com.fincons.service.employee.ICreateNewEmployeeRandom;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@EnableRetry
public class JobScheduler {

    Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    @Autowired
    private IEmailHireDate iEmailHireDate;
    @Autowired
    private IEmailBirthDate iEmailBirtheDate;
    @Autowired
    private ICreateNewEmployeeRandom iCreateNewEmployeeRandom;

    @Scheduled(cron = "${jobScheduler.JobScheduler.emailSenderBirth}")
    @SchedulerLock(name = "birthEmailScheduler", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    @Retryable(retryFor = IllegalArgumentException.class, maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public void emailSenderBirth() throws IllegalArgumentException {
        logger.info("Looking for Birthdays...");
        iEmailBirtheDate.sendBirthdayGreetings();
        logger.info("All emails were sent to {} ", LocalDate.now());
    }

    @Scheduled(cron = "${jobScheduler.JobScheduler.emailSenderHire}")
    @SchedulerLock(name = "hireEmailScheduler", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    @Retryable(retryFor = IllegalArgumentException.class, maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public void emailSenderHire() throws IllegalArgumentException {
        logger.info("Looking for Anniversaries...");
        iEmailHireDate.sendAnniversaryGreetings();
        logger.info("All emails were sent to {} ", LocalDate.now());
    }

    @Scheduled(cron = "${jobScheduler.JobScheduler.newRandomEmployee}")
    public void newEmployeeRandom() throws IllegalArgumentException {
        logger.info("Creating new employees...");
        iCreateNewEmployeeRandom.createNewRandomEmployee(5);
    }

    @Recover
    public void recover(IllegalArgumentException r) {
        int i = 0;
        logger.error("Email has reached the maximum number of retry attempts. {}", i);
    }
}