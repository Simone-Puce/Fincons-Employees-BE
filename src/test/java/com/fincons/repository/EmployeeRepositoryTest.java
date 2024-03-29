package com.fincons.repository;

import com.fincons.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testBirthDate() {
        LocalDate birthday = LocalDate.parse("1962-05-29");

        List<Employee> emps = employeeRepository.findEmployeesByTodayBirthday(birthday);
        System.out.println(emps);
        assertThat(emps).hasSize(1);
    }

    @Test
    void testHireDate() {
        LocalDate hireDate = LocalDate.parse("1985-02-21");

        List<Employee> emps = employeeRepository.findEmployeesByTodayStartDate(hireDate);
        System.out.println(emps);
        assertThat(emps).hasSize(1);
    }

    @Test
    void testEmptyList() {
        LocalDate hireDate = LocalDate.parse("2004-02-20");

        List<Employee> emps = employeeRepository.findEmployeesByTodayStartDate(hireDate);
        System.out.println(emps);
        assertThat(emps).isEmpty();
    }

    @Test
    void testList() {
        LocalDate startOfYear = LocalDate.parse("2023-01-01");
        LocalDate endOfYear = LocalDate.parse("2023-12-31");

        long daysInYear = startOfYear.until(endOfYear, ChronoUnit.DAYS);

        long totalEmployees = 0;

        for (LocalDate date = startOfYear; !date.isAfter(endOfYear); date = date.plusDays(1)) {
            int employeesCount = employeeRepository.findEmployeesByTodayStartDate(date).size();
            totalEmployees += employeesCount;
        }

        System.out.println("Numero di giorni nell'anno: " + daysInYear);
        System.out.println("Numero totale di dipendenti: " + totalEmployees);
    }

    @Test
    void testListBirth() {
        LocalDate startOfYear = LocalDate.parse("2020-01-01");
        LocalDate endOfYear = LocalDate.parse("2020-12-31");

        long daysInYear = startOfYear.until(endOfYear, ChronoUnit.DAYS);

        long totalEmployees = 0;

        for (LocalDate date = startOfYear; !date.isAfter(endOfYear); date = date.plusDays(1)) {
            int employeesCount = employeeRepository.findEmployeesByTodayBirthday(date).size();
            totalEmployees += employeesCount;
        }
        System.out.println("Numero di giorni nell'anno: " + daysInYear);
        System.out.println("Numero totale di dipendenti: " + totalEmployees);
    }
}