package com.t2m.g2nee.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class G2neeApplication {

    public static void main(String[] args) {
        SpringApplication.run(G2neeApplication.class, args);
    }

}
