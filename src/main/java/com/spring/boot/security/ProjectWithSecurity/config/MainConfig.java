//package com.spring.boot.security.ProjectWithSecurity.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class MainConfig {
//    @Value("${spring.mail.host}")
//    private String host;
//
//    @Value("${spring.mail.username}")
//    private String username;
//
//    @Value("${spring.mail.password}")
//    private String password;
//
//    @Value("${spring.mail.port}")
//    private int port;
//
//    @Value("${spring.mail.protocol}")
//    private String protocol;
//
//    @Value("${mail.debug}")
//    private String debug;
//
//    @Bean
//    public JavaMailSender getMailSender(){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//
//        Properties properties = mailSender.getJavaMailProperties();
//
//        properties.setProperty("mail.transport.protocol" , protocol);
//        // Прописываем для ситуаций когда что-то пошло не так то мы в логах можем посмотреть что именно
//        // в реальном проекте эту переменную лучше отключить
//        properties.setProperty("mail.debug", debug);
//
//        return mailSender;
//    }
//}
