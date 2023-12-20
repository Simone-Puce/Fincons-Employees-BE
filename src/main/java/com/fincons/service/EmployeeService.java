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

    ResponseEntity<EmployeeDTO> findById(long id);
    ResponseEntity<List<EmployeeDTO>> findAll();
    ResponseEntity<EmployeeDTO> save(Employee employee);
    ResponseEntity<EmployeeDTO> update(long id, Employee employee);
    ResponseEntity<EmployeeDTO> deleteById(long id);

    ResponseEntity<List<ProjectDTO>> findAllEmployeeProjects(long id);
    ResponseEntity<List<EmployeeProjectDTO>> getAllEmployeeProject();
    ResponseEntity<EmployeeProjectDTO> addEmployeeProject(long idEmployee, long idProject);
    EmployeeProjectDTO updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO);
    ResponseEntity<EmployeeDTO> deleteEmployeeProject(long idEmployee, long idProject);
}
