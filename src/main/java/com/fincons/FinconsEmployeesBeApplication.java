package com.fincons;

import com.fincons.entity.Employee;
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class FinconsEmployeesBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinconsEmployeesBeApplication.class, args);
	}
	/*
	@Bean
	CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository, ProjectRepository projectRepository){

		return args -> {

			boolean IS_CREATE= false;
			if (IS_CREATE){


				//CREATE
				Employee employee1=new Employee();
				employee1.setFirstName("Nome1");
				employee1.setLastName("Cognome1");
				employee1.setGender("Femmina");

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date setBirthDate = formatter.parse("1999-01-30");
				employee1.setBirthDate(setBirthDate);
				Date setStartDate = formatter.parse("2010-01-30");
				employee1.setStartDate(setStartDate);




				employeeRepository.save(employee1);

			}

		};

	 */
	}


