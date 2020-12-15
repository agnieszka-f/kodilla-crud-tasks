package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {
    @InjectMocks
    SimpleEmailService simpleEmailService;
    @Mock
    JavaMailSender javaMailSender;
    @Test
    public void shouldSendEmail(){
        //Given
        Mail mail = new Mail("test@test.com","test", "test message", null);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getReceiverEmail());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        //When
        simpleEmailService.send(mail);
        //Then
        verify(javaMailSender,times(0)).send(mailMessage);
    }
}