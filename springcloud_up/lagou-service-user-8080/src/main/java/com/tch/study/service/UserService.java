package com.tch.study.service;

import com.tch.study.pojo.LagouUser;

public interface UserService {
    LagouUser register(String email, String password, String code);

    boolean isRegistered(String email);

    LagouUser login(String email, String password);

    String info(String token);

    String createToken(LagouUser lagouUser);

}
