package com.fincons.service;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmployeeService {

    ResponseEntity<Object> findById(long id);
    ResponseEntity<Object> findAll();
    ResponseEntity<Object> save(Employee employee);
    ResponseEntity<Object> update(long id, Employee employee);
    ResponseEntity<Object> deleteById(long id);

    ResponseEntity<Object> findAllEmployeeProjects(long id);
    ResponseEntity<Object> getAllEmployeeProject();
    ResponseEntity<Object> addEmployeeProject(long idEmployee, long idProject);
    ResponseEntity<Object> updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO);
    ResponseEntity<Object> deleteEmployeeProject(long idEmployee, long idProject);
}
