package com.spring.boot.security.ProjectWithSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MainSenderService {
    @Autowired
    private JavaMailSender mailSender;

    // Отправитель
    @Value("${spring.mail.username}")
    private String username;

    // Отправляемое письмо, которое содержит String emailTo - адрес получателя, String subject - тема письма,
    // String message - текст письма
    //
//    @Async // Для быстрой отправки почти вроде
    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username); // отправитель
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
