package com.crud.tasks.service;

import com.crud.tasks.trello.config.AdminConfig;
import com.crud.tasks.trello.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;

    public String buildTrelloCardEmail(String message){
        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:8888/crud");
        context.setVariable("button","Visit website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("company_name",companyConfig.getCompanyName());
        context.setVariable("company_email",companyConfig.getCompanyEmail());
        context.setVariable("company_phone",companyConfig.getCompanyPhone());
        context.setVariable("message_preview", createMessagePreview(message));
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
    private String createMessagePreview(String message){
        if(message.length()>150) return message.substring(0,149);
        else return message;
    }
}
