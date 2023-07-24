package com.mohey.memberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class MemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }

}
