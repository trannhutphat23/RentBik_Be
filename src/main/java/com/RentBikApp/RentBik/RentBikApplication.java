package com.RentBikApp.RentBik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RentBikApplication {
	public static void main(String[] args) {
		SpringApplication.run(RentBikApplication.class, args);
		System.out.println("Running on Port 8080");
	}
}
