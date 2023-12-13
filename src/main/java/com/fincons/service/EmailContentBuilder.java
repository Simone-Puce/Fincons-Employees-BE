package com.fincons.service;

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

        //1
        //for (String key : map.keySet()) {
        //    context.setVariable(key, map.get(key));
        //}

        //2
        //map.forEach((key, value) -> {
        //    context.setVariable(key, value);
        //});

        //3
        //map.forEach(context::setVariable);

        return templateEngine.process("email-template", context);
    }
}