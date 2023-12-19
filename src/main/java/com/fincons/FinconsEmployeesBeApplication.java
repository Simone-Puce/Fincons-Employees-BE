package com.fincons;

import com.fincons.entity.Employee;
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class FinconsEmployeesBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinconsEmployeesBeApplication.class, args);
	}
}


