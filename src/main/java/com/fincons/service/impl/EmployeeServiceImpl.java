package com.fincons.service.impl;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.EmployeeProjectService;
import com.fincons.service.EmployeeService;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService, EmployeeProjectService {

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
    public Employee employeeAddProject(@RequestParam long id, @RequestBody Project projectRequest) {
        Employee employee = employeeRepository.findById(id);
        Set<Project> projects = employee.getProject(); // Ottenere i progetti attuali del dipendente
        projects.add(projectRequest);  // Aggiungere il nuovo progetto
        employee.setProject(projects); // Impostare la collezione aggiornata di progetti
        return employeeRepository.save(employee);

    }
}
