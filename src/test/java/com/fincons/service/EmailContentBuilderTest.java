package com.fincons.service;

import com.fincons.entity.Employee;
import com.fincons.service.email.EmailContentBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmailContentBuilderTest {

    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @Test
    void buildEmailContent() {
        Employee emp = new Employee();
        emp.setFirstName("Pippo");
        emp.setLastName("Baudo");
        Map<String, Object> textMessage = new HashMap<>();
        textMessage.put("name", emp.getFirstName());
        textMessage.put("lastName", emp.getLastName());

        String email = emailContentBuilder.buildEmailContent(textMessage);
        System.out.println(email);

        assertThat(email).contains(emp.getFirstName());
    }
}
