package com.tch.study.service.impl;

import com.tch.study.dao.LagouAuthCodeDao;
import com.tch.study.pojo.LagouAuthCode;
import com.tch.study.service.CodeService;
import com.tch.study.service.EmailService;
import com.tch.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private LagouAuthCodeDao lagouAuthCodeDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Override
    public boolean createCode(String email) {

        boolean registered = userService.isRegistered(email);
        if(registered){
            return false;
        }
        //生成六位随机数
        Integer random = (int)(Math.random() * 1000000);
        String code = ""+random;
        while(code.length()<6){
            code = "0"+code;
        }

        //持久化code
        LagouAuthCode lagouAuthCode = new LagouAuthCode();
        lagouAuthCode.setCode(code);
        lagouAuthCode.setCreatetime(new Date());
        lagouAuthCode.setEmail(email);
        lagouAuthCode.setExpiretime(new Date(System.currentTimeMillis()+1000*60*10));
        lagouAuthCodeDao.save(lagouAuthCode);
        emailService.sendValidateCodeEmail(email,code);
        return true;
    }

    @Override
    public Integer validateCode(String email, String code) {
        LagouAuthCode lagouAuthCode = new LagouAuthCode();
        lagouAuthCode.setEmail(email);
        lagouAuthCode.setCode(code);
        Example<LagouAuthCode> example = Example.of(lagouAuthCode);
        List<LagouAuthCode> all = lagouAuthCodeDao.findAll(example);
        if(all.size()==0){
            return 1;
        }else{
            for (LagouAuthCode authCode : all) {
                if(authCode.getExpiretime().getTime()>=System.currentTimeMillis()){
                    return 0;
                }
            }
        }
        return 2;
    }
}
