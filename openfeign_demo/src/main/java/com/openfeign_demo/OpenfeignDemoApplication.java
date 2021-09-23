package com.openfeign_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.openfeign_demo"})
public class OpenfeignDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfeignDemoApplication.class, args);
	}

}
