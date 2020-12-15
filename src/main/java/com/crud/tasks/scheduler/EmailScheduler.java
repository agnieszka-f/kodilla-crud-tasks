package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
    //@Scheduled(fixedDelay = 10000)
    public void sendInformationEmail(){
        simpleEmailService.send(getInformationEmail());
    }
    private Mail getInformationEmail(){
        return new Mail(adminConfig.getAdminMail(),SUBJECT,getInformationMessage(),null);
    }
    private String getInformationMessage(){
        return "Currently in database you got: "+ taskRepository.count()+" task".concat(taskRepository.count()!=1?"s.":".");
    }
}
