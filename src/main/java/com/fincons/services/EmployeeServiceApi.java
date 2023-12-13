package com.fincons.services;

import com.fincons.entities.Employee;
import com.fincons.models.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface EmployeeServiceApi {


    Employee createEmployee(EmployeeDTO employeeDto);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);


    Employee addEmployee(EmployeeDTO newEmployee);

    ResponseEntity<Employee> updateEmployee(Long id, Employee employeeDetails);

    ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id);

}


