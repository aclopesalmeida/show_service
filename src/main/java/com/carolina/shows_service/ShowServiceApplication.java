package com.carolina.shows_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class ShowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowServiceApplication.class, args);
    }

}
