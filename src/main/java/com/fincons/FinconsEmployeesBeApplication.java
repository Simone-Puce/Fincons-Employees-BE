package com.fincons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

@SpringBootApplication
public class FinconsEmployeesBeApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FinconsEmployeesBeApplication.class, args);


	}

}
