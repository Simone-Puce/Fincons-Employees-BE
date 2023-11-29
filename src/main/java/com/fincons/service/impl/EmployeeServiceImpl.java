package com.fincons.service.employee;

import com.fincons.entity.Employee;
import com.fincons.repository.EmployeeRepository;
import com.fincons.service.employee.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
