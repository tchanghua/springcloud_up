package com.tch.study.service;

import com.tch.study.service.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "lagou-service-user",path = "/api/user",fallback = UserServiceImpl.class)
public interface UserService  {

    @PostMapping("/info/{token}")
    String checkToken(@PathVariable("token") String token);
}
