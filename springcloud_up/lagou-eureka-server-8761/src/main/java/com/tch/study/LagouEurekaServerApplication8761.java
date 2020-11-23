package com.tch.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LagouEurekaServerApplication8761 {
    public static void main(String[] args) {
        SpringApplication.run(LagouEurekaServerApplication8761.class,args);
    }
}
