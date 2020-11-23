package com.tch.study.controller;

import com.tch.study.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping("/create/{email}")
    public boolean create(@PathVariable("email") String email){
        return codeService.createCode(email);
    }
    @PostMapping("/validate/{email}/{code}")
    public Integer validate(@PathVariable("email") String email, @PathVariable("code") String code){

        return codeService.validateCode(email,code);
    }
}
