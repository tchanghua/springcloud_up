package com.tch.study.service;

import com.tch.study.service.impl.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "lagou-service-user",path = "/api/user",fallback = UserServiceFallback.class) // 使⽤fallback的时候，类上的@RequestMapping的url前缀限定，改成配置在@FeignClient的path属性中
public interface UserService {
    @PostMapping("/isRegistered/{email}")
    boolean isRegistered(@PathVariable("email") String email);
}
