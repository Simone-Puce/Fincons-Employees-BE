package com.fincons.jobScheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
@SpringBootTest
class JobSchedulerTest {

    @Autowired
    private JobScheduler jobScheduler;

    @Test
    //@Disabled
    void testEmailSenderBirth() throws RuntimeException{
        jobScheduler.emailSenderBirth();
    }

    @Test
    //@Disabled
    void testEmailSenderHire() throws RuntimeException{
        jobScheduler.emailSenderHire();
    }

    @Test
    //@Disabled
    void testEmailSenderBirthRetry() throws RuntimeException{
        assertDoesNotThrow(() -> jobScheduler.emailSenderBirth());
    }

    @Test
    //@Disabled
    void testEmailSenderHireRetry() throws RuntimeException{
        assertDoesNotThrow(() -> jobScheduler.emailSenderHire());
    }
    @Test
    void testNewEmployeeRandom() throws Exception {
        jobScheduler.newEmployeeRandom();
    }
}