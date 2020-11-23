package com.tch.study.service;

public interface EmailService {
    boolean sendValidateCodeEmail(String email,String code);
}
