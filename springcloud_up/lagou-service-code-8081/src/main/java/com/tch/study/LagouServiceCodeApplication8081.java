package com.tch.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan("com.tch.study.pojo")
public class LagouServiceCodeApplication8081 {
    public static void main(String[] args) {
        SpringApplication.run(LagouServiceCodeApplication8081.class,args);
    }
}
