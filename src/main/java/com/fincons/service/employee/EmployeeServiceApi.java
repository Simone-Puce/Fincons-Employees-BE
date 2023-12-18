package com.fincons.service.employee;


import com.fincons.entity.Employee;
import com.fincons.model.EmployeeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeServiceApi {

    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeById(Long id);

    Employee createEmployee(EmployeeDto employeeDto) throws Exception;

    ResponseEntity<Employee> updateEmployee(Long id, Employee employeeDetails);

    void deleteEmployee(Long id);

}
