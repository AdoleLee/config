package com.adole;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ConfigDemoApplication {

    public static void main(String[] args) {
        ConfigDemoApplication c =new ConfigDemoApplication();

        SpringApplication.run(ConfigDemoApplication.class, args);
    }
}
