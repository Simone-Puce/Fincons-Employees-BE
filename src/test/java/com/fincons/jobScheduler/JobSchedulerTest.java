package com.fincons.jobScheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
@SpringBootTest
public class JobSchedulerTest {

    @Autowired
    private JobScheduler jobScheduler;

    @Test
    //@Disabled
    public void testEmailSenderBirth() throws RuntimeException{
        jobScheduler.emailSenderBirth();
    }

    @Test
    //@Disabled
    public void testEmailSenderHire() throws RuntimeException{
        jobScheduler.emailSenderHire();
    }

    @Test
    //@Disabled
    public void testEmailSenderBirthRetry() throws RuntimeException{
        assertDoesNotThrow(() -> jobScheduler.emailSenderBirth());
    }

    @Test
    //@Disabled
    public void testEmailSenderHireRetry() throws RuntimeException{
        assertDoesNotThrow(() -> jobScheduler.emailSenderHire());
    }
}