package com.medixpress.pharma_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PharmaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaServiceApplication.class, args);
	}

}
