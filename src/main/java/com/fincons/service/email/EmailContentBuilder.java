package com.fincons.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Map;

@Component
public class EmailContentBuilder {

    @Autowired
    private TemplateEngine templateEngine;

    public String buildEmailContent(Map<String, Object> emailContent) {
        Context context = new Context();
        context.setVariables(emailContent);

        return templateEngine.process("email-template", context);
    }
}