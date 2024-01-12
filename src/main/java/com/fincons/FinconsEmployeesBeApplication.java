package com.fincons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
public class FinconsEmployeesBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinconsEmployeesBeApplication.class, args);
	}
}


