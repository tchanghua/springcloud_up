package com.tch.study.service.impl;

import com.tch.study.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserService {
    @Override
    public boolean isRegistered(String email) {
        return false;
    }
}
