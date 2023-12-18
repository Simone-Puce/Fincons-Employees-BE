package com.fincons.jobScheduler;

import com.fincons.service.email.EmailService;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@EnableRetry
@PropertySource(value = "email.properties")
public class JobScheduler {

    Logger logger = LoggerFactory.getLogger(RuntimeException.class);

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "${jobScheduler.JobScheduler.emailSenderBirth}")
    @SchedulerLock(name = "birthEmailScheduler", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    @Retryable(retryFor = RuntimeException.class, backoff = @Backoff(delay = 1800000))
    public void emailSenderBirth() throws RuntimeException{
            System.out.println("Sto cercando Compleanni");
            emailService.sendGreetingsToEmployeesBirth();
            logger.info("All emails were sent to " + LocalDate.now());
    }

    @Scheduled(cron = "${jobScheduler.JobScheduler.emailSenderHire}")
    @SchedulerLock(name = "hireEmailScheduler",lockAtLeastFor = "PT1M",lockAtMostFor = "PT5M")
    @Retryable(retryFor = RuntimeException.class, backoff = @Backoff(delay = 1800000))
    public void emailSenderHire() throws RuntimeException{
        System.out.println("Sto cercando Anniversari");
        emailService.sendGreetingsToEmployeesHire();
        logger.info("All emails were sent to " + LocalDate.now());
    }

    @Recover
    public void recoverFromFixedRetry(RuntimeException e) {
        logger.info("Tentativi di invio email falliti");
    }
}
