package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class Starter {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Starter.class, args);
    }
}
