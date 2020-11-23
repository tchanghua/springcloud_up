package com.tch.study.service;

public interface CodeService {

    boolean createCode(String email);

    Integer validateCode(String email, String code);


}
