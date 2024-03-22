package com.t2m.g2nee.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class G2neeApplication {

	public static void main(String[] args) {
		SpringApplication.run(G2neeApplication.class, args);
	}

}
