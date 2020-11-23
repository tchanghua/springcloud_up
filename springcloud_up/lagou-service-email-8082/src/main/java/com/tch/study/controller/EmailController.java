package com.tch.study.controller;

import com.tch.study.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @PostMapping("/{email}/{code}")
    public boolean sendValidateCodeEmail(@PathVariable("email") String email,@PathVariable("code") String code){

        try {
            return emailService.sendValidateCodeEmail(email,code);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
