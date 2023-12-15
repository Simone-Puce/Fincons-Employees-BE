package com.fincons.service.impl;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.ResourceNotFoundException;
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
    public Employee update(long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Dipartimento con ID " + id + " non trovato");
        } else {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setBirthDate(employee.getBirthDate());
            existingEmployee.setStartDate(employee.getStartDate());
            existingEmployee.setEndDate(employee.getEndDate());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setRole(employee.getRole());
        }
        return employeeRepository.save(existingEmployee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Project> findAllEmployeeProjects(long id) {
        return employeeRepository.findProjectByEmployeeId(id);
    }

    @Override
    public List<EmployeeProjectDTO> getAllEmployeeProject() {
        return employeeRepository.getAllEmployeeProject();
    }

    @Override
    public Employee addEmployeeProject(long idEmployee, long idProject) {
        Employee employee = employeeRepository.findById(idEmployee);
        Project project = projectRepository.findById(idProject);
        employee.getProjects().add(project);
        return employeeRepository.save(employee);

    }

    @Override
    public Employee deleteEmployeeProject(long idEmployee, long idProject) {
        Employee employee = employeeRepository.findById(idEmployee);
        Project project = projectRepository.findById(idProject);
        employee.getProjects().remove(project);
        return employeeRepository.save(employee);
    }


}
