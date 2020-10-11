package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);
    @Autowired
    JavaMailSender javaMailSender;

    public void send(final Mail mail){
        LOGGER.info("Rozpoczęcie wysyłania e-mail.");
        try{
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("E-mail został pomyślnie wysłany.");
        } catch(MailException m){
            LOGGER.error("Błąd podczas wysyłana e-maila: ", m.getMessage(), m);
        }
    }
    private SimpleMailMessage createMailMessage(final Mail mail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(mail.getMessage());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setTo(mail.getReceiverEmail());
        if(mail.getToCC()!=null) {
            mailMessage.setCc(mail.getToCC());
        }
        return mailMessage;
    }
}
