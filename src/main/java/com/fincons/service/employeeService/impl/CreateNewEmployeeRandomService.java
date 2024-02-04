package com.fincons.service.employeeService.impl;


import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import com.fincons.entity.Position;
import com.fincons.service.employeeService.DepartmentService;
import com.fincons.service.employeeService.ICreateNewEmployeeRandom;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.employeeService.PositionService;
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
    private EmployeeService EmployeeService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DateHelper dateHelper;

    Logger logger = LoggerFactory.getLogger(CreateNewEmployeeRandomService.class);


    @Override
    public void createNewRandomEmployee(int nEmployee) throws IllegalArgumentException {
        Faker faker = new Faker(new Random());
        List<Employee> employeeList = new ArrayList<>();

        Department department = new Department();
        department.setId(3L);
        Position position = new Position();
        position.setId(5L);

        for (int i = 0; i < nEmployee; i++) {
            String name = faker.name().firstName();
            String surname = faker.name().lastName();
            logger.info("----Employee:  {} {}", name, surname);
            String newEmail = name + "." + surname + "@gmail.com";

            Date dateBirth = faker.date().birthday();
            LocalDate birthDay = dateHelper.convertToLocalDateViaInstant(dateBirth);
            logger.info("Birthday Date: {}", birthDay);
            LocalDate startDay = dateHelper.createStartDay(birthDay);
            logger.info("Assumption date: {} {}", startDay, startDay.getDayOfWeek().name());
            LocalDate endDay = startDay.plusYears(40);
            String gender = dateHelper.getRandomGender();



            Employee emp = new Employee();
            emp.setFirstName(name);
            emp.setLastName(surname);
            emp.setEmail(newEmail.toLowerCase());
            emp.setBirthDate(birthDay);
            emp.setStartDate(startDay);
            emp.setGender(gender);
            emp.setEndDate(endDay);
            emp.setDepartment(department);
            emp.setPosition(position);

            employeeList.add(emp);
            EmployeeService.createEmployee(emp);
        }
        logger.info("They were saved: {} Employee", employeeList.size());
    }
}
