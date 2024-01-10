package com.fincons.service.employee;


import com.fincons.entity.Employee;
import com.fincons.utility.DateHelper;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CreateNewEmployeeRandomService implements ICreateNewEmployeeRandom {

    @Autowired
    private IEmployeeService IEmployeeService;

    @Autowired
    private DateHelper dateHelper;

    Logger logger = LoggerFactory.getLogger(CreateNewEmployeeRandomService.class);


    @Override
    public void createNewRandomEmployee(int nEmployee) throws RuntimeException {
        Faker faker = new Faker(new Random());
        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < nEmployee; i++) {
            String name = faker.name().firstName();
            String surname = faker.name().lastName();
            logger.info("----Employee:  {} {}", name, surname);
            String newEmail = name + "." + surname + "@gmail.com";

            Date dateBirth = faker.date().birthday();
            LocalDate birthDay = dateHelper.convertToLocalDateViaInstant(dateBirth);
            logger.info("Birthday Date: {}", birthDay);
            LocalDate hireDay = dateHelper.createHireDay(birthDay);
            logger.info("Assumption date: {} {}", hireDay, hireDay.getDayOfWeek().name());

            Employee emp = new Employee();
            emp.setFirstName(name);
            emp.setLastName(surname);
            emp.setEmail(newEmail.toLowerCase());
            emp.setBirthDate(birthDay);
            emp.setHireDate(hireDay);

            employeeList.add(emp);
            IEmployeeService.createEmployee(emp);
        }
        logger.info("They were saved: {} Employee", employeeList.size());
    }
}
