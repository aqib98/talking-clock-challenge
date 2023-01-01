package com.lbg.talkingclock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
public class TalkingClockApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalkingClockApplication.class, args);
    }
}
