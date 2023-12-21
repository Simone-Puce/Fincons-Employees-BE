package com.fincons.service.employee;


import com.fincons.entity.Employee;
import com.fincons.model.EmployeeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IEmployeeService {

    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeById(Long id);


    Employee createEmployee(Employee employee) throws Exception;

    ResponseEntity<Employee> updateEmployee(Long id, Employee employee);

    ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id);
}