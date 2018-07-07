package com.adole;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest {

    @Value("${say}")
    private String test;

    @Scheduled(fixedRate = 1000)
    public void testa(){
        System.out.println(test);
    }
}
