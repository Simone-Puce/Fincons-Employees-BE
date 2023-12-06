package com.fincons.service;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public interface EmployeeService {

    List<Employee> findAll();

    Employee saveEmployee(Employee employee);

    Employee findById(long id);


    List<Project> findAllEmployeeProjects(long id);

    Employee addEmployeeProject(long idEmployee,long idProject);


    Employee deleteEmployeeProject(long idEmployee, long idProject);
}
