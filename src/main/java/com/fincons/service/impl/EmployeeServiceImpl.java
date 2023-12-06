package com.fincons.service.impl;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Project> findAllEmployeeProjects(long id) {
        return employeeRepository.findProjectByEmployeeId(id);
    }

    @Override
    public Employee addEmployeeProject(long idEmployee, long idProject) {
        Employee employee = employeeRepository.findById(idEmployee);
        Project project = projectRepository.findById(idProject);
        employee.getProject().add(project);
        return employeeRepository.save(employee);

    }

    @Override
    public Employee deleteEmployeeProject(long idEmployee, long idProject) {
        Employee employee = employeeRepository.findById(idEmployee);
        Project project = projectRepository.findById(idProject);
        employee.getProject().remove(project);
        return employeeRepository.save(employee);
    }

}
