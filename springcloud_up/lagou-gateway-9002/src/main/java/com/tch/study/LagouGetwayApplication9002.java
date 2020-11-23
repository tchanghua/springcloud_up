package com.tch.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan("caifuwen.study.scn.pojo")
public class LagouGetwayApplication9002 {
    public static void main(String[] args) {
        SpringApplication.run(LagouGetwayApplication9002.class,args);
    }
}
