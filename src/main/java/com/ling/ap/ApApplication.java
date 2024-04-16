package com.ling.ap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApApplication.class, args);
    }

}
