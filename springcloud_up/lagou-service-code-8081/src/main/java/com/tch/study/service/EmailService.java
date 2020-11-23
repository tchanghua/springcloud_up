package com.tch.study.service;

import com.tch.study.service.impl.EmailServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "lagou-service-email",path = "/api/email",fallback = EmailServiceFallback.class) // 使⽤fallback的时候，类上的@RequestMapping的url前缀限定，改成配置在@FeignClient的path属性中
public interface EmailService {
    @PostMapping("/{email}/{code}")
    boolean sendValidateCodeEmail(@PathVariable("email") String email, @PathVariable("code") String code);
}
