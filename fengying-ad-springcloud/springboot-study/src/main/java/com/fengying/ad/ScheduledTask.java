package com.fengying.ad;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 1000)
    public void helloWorld(){
        System.out.println("helloWorld!"+System.currentTimeMillis());
    }
}
