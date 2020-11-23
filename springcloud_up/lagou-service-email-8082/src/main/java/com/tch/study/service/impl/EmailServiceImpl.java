package com.tch.study.service.impl;

import com.tch.study.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public boolean sendValidateCodeEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("注册验证码");
        message.setText(code);
        mailSender.send(message);
        return true;
    }
}
