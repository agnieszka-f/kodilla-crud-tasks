package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

import static java.util.Optional.ofNullable;

@Service
public class SimpleEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail){
        LOGGER.info("Rozpoczęcie wysyłania e-mail.");
        try{
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("E-mail został pomyślnie wysłany.");
        } catch(MailException m){
            LOGGER.error("Błąd podczas wysyłana e-maila: ", m.getMessage(), m);
        }
    }
    private MimeMessagePreparator createMimeMessage(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getReceiverEmail());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()),true);
        };
    }
    private SimpleMailMessage createMailMessage(final Mail mail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setTo(mail.getReceiverEmail());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        ofNullable(mail.getToCC()).ifPresent(toCC -> mailMessage.setCc(mail.getToCC()));

        return mailMessage;
    }
}
