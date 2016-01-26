package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class Starter {
    @RequestMapping("/")
    String home() {
        return "index";
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Starter.class, args);
    }
}
