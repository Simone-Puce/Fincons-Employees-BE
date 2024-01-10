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

    ResponseEntity<Object> getEmployeeById(long id);
    ResponseEntity<Object> getAllEmployees();
    ResponseEntity<Object> createEmployee(Employee employee);
    ResponseEntity<Object> updateEmployeeById(long id, Employee employee);
    ResponseEntity<Object> deleteEmployeeById(long id);

    ResponseEntity<Object> findAllEmployeeProjects(long id);
    ResponseEntity<Object> getAllEmployeeProject();
    ResponseEntity<Object> addEmployeeProject(long idEmployee, long idProject);
    ResponseEntity<Object> updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO);
    ResponseEntity<Object> deleteEmployeeProject(long idEmployee, long idProject);
}
