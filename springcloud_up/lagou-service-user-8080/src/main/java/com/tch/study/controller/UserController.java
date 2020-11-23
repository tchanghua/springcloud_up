package com.tch.study.controller;

import com.tch.study.pojo.LagouUser;
import com.tch.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/{email}/{password}/{code}")
    public boolean register(@PathVariable("email") String email, @PathVariable("password")String password, @PathVariable("code") String code, HttpServletResponse response){
        LagouUser lagouUser = userService.register(email, password, code);
        if(null != lagouUser){
            String authToken = userService.createToken(lagouUser);
            Cookie cookie=new Cookie("token",authToken);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60*60*24*7);
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    @PostMapping("/isRegistered/{email}")
    public boolean isRegistered(@PathVariable("email") String email){
        return userService.isRegistered(email);
    }

    @PostMapping("/login/{email}/{password}")
    public boolean login(@PathVariable("email")String email, @PathVariable("password")String password, HttpServletResponse response){
        LagouUser lagouUser = userService.login(email, password);
        if(lagouUser!=null){
            String authToken = userService.createToken(lagouUser);
            Cookie cookie=new Cookie("token",authToken);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60*60*24*7);
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    @PostMapping("/info")
    public String info(@CookieValue("token") String token){
        return userService.info(token);
    }

    @PostMapping("/info/{token}")
    public String checkToken(@PathVariable("token") String token){
        return userService.info(token);
    }

}
