package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.trello.config.AdminConfig;
import com.crud.tasks.trello.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;
    @Autowired
    private DbService dbService;

    public String buildTrelloCardEmail(String message){
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection whit Trello account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:8888/crud");
        context.setVariable("button","Visit website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("company_name",companyConfig.getCompanyName());
        context.setVariable("company_email",companyConfig.getCompanyEmail());
        context.setVariable("company_phone",companyConfig.getCompanyPhone());
        context.setVariable("message_preview", createMessagePreview(message));
        context.setVariable("show_button",false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config",adminConfig);
        context.setVariable("application_functionality", functionality);

        if(message.contains("Currently in database you got")){
            List<Task> tasks = dbService.getAllTasks();
            if(tasks.size() > 0){
                context.setVariable("tasks", tasks);
                context.setVariable("is_display_tasks", true);
            }
        } else{
            context.setVariable("is_display_tasks", false);
        }

        return templateEngine.process("mail/created-trello-card-mail", context);
    }
    private String createMessagePreview(String message){
        if(message.length()>150) return message.substring(0,149);
        else return message;
    }
}
