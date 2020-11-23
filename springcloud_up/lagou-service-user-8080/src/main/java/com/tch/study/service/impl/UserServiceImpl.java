package com.tch.study.service.impl;

import com.tch.study.dao.LagouTokenDao;
import com.tch.study.dao.LagouUserDao;
import com.tch.study.pojo.LagouToken;
import com.tch.study.pojo.LagouUser;
import com.tch.study.service.CodeService;
import com.tch.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private LagouTokenDao lagouTokenDao;

    @Autowired
    private LagouUserDao lagouUserDao;

    @Autowired
    private CodeService codeService;


    @Override
    public LagouUser register(String email, String password, String code) {
        Integer validate = codeService.validate(email, code);
        if(validate!=0){
            return null;
        }
        boolean registered = isRegistered(email);
        if(registered){
            return null;
        }
        LagouUser lagouUser = new LagouUser();
        lagouUser.setEmail(email);
        lagouUser.setCreatetime(new Date());
        lagouUser.setPassword(password);
        lagouUserDao.save(lagouUser);
        return lagouUser;
    }

    @Override
    public boolean isRegistered(String email) {
        LagouUser lagouUser = new LagouUser();
        lagouUser.setEmail(email);
        Example<LagouUser> example = Example.of(lagouUser);
        Optional<LagouUser> one = lagouUserDao.findOne(example);
        System.out.println(one.isPresent());
        return one.isPresent();
    }

    @Override
    public LagouUser login(String email, String password) {
        LagouUser lagouUser = new LagouUser();
        lagouUser.setEmail(email);
        lagouUser.setPassword(password);
        Example<LagouUser> example = Example.of(lagouUser);
        Optional<LagouUser> one = lagouUserDao.findOne(example);
        if(one.isPresent()){
            return one.get();
        }else{
            return null;
        }
    }

    @Override
    public String info(String token) {
        LagouToken lagouToken = new LagouToken();
        lagouToken.setToken(token);
        Example<LagouToken> example = Example.of(lagouToken);
        Optional<LagouToken> one = lagouTokenDao.findOne(example);
        if(one.isPresent()){
            return one.get().getEmail();
        }else{
            return null;
        }
    }

    @Override
    public  String createToken(LagouUser lagouUser){
        String token = UUID.randomUUID().toString();
        LagouToken lagouToken = new LagouToken();
        lagouToken.setToken(token);
        lagouToken.setEmail(lagouUser.getEmail());
        lagouTokenDao.save(lagouToken);
        return token;
    }
}
