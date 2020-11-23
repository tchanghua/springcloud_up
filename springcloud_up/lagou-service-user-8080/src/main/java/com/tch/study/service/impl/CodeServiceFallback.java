package com.tch.study.service.impl;

import com.tch.study.service.CodeService;
import org.springframework.stereotype.Component;

@Component
public class CodeServiceFallback implements CodeService {
    @Override
    public Integer validate(String email, String code) {
        return 2;
    }
}
