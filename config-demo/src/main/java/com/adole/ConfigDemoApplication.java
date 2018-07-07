package com.adole;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigDemoApplication {
    @Value("${say}")
    public static String test;

    public static void main(String[] args) {
        SpringApplication.run(ConfigDemoApplication.class, args);
        System.out.println(test);
    }
}
