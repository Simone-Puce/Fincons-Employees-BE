package com.fincons.service;

import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmployeeService {

    List<Employee> findAll();

    Employee save(Employee employee);

    Employee findById(long id);
    Employee update(long id, Employee employee);
    void deleteById(long id);

    List<Project> findAllEmployeeProjects(long id);

    List<EmployeeProjectDTO> getAllEmployeeProject();

    Employee addEmployeeProject(long idEmployee,long idProject);

    Employee deleteEmployeeProject(long idEmployee, long idProject);

    EmployeeProjectDTO updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO);
}
