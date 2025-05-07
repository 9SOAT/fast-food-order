package com.fiap.challenge.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fiap.challenge.order")
public class FastFoodOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastFoodOrderApplication.class, args);
	}

}
