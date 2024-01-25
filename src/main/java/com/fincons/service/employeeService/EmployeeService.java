package com.fincons.service.employeeService;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmployeeService {

    ResponseEntity<Object> getEmployeeById(String idEmployee);
    ResponseEntity<Object> getEmployeeByEmail(String email);
    ResponseEntity<Object> getAllEmployees();
    ResponseEntity<Object> createEmployee(EmployeeDTO employeeDTO);
    ResponseEntity<Object> updateEmployeeById(String idEmployee, EmployeeDTO employeeDTO);
    ResponseEntity<Object> deleteEmployeeById(String idEmployee);
    ResponseEntity<Object> findAllEmployeeProjects(String idEmployee);
    ResponseEntity<Object> getAllEmployeeProject();
    ResponseEntity<Object> addEmployeeProject(String idEmployee, String idProject);
    ResponseEntity<Object> updateEmployeeProject(String idEmployee, String idProject, EmployeeProjectDTO employeeProjectDTO);
    ResponseEntity<Object> deleteEmployeeProject(String idEmployee, String idProject);
}
