package com.tch.study.service.impl;

import com.tch.study.service.EmailService;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceFallback implements EmailService {
    @Override
    public boolean sendValidateCodeEmail(String email, String code) {
        return false;
    }
}
