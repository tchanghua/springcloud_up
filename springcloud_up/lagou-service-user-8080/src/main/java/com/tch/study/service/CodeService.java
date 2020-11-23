package com.tch.study.service;

import com.tch.study.service.impl.CodeServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "lagou-service-code",path = "/api/code",fallback = CodeServiceFallback.class)
public interface CodeService {
    @PostMapping("/validate/{email}/{code}")
    Integer validate(@PathVariable("email") String email, @PathVariable("code") String code);
}
