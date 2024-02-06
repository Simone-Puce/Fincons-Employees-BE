package com.fincons.service.employeeService;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.entity.Project;

import java.util.List;

public interface EmployeeService {

    Employee getEmployeeById(String idEmployee);
    Employee getEmployeeByEmail(String email);
    List<Employee> getAllEmployees();
    Employee createEmployee(EmployeeDTO employeeDTO);
    Employee updateEmployeeById(String idEmployee, EmployeeDTO employeeDTO);
    void deleteEmployeeById(String idEmployee);
    List<Project> findAllEmployeeProjects(String idEmployee);
    List<EmployeeProjectDTO> getAllEmployeeProject();
    EmployeeProjectDTO addEmployeeProject(String idEmployee, String idProject);
    EmployeeProjectDTO updateEmployeeProject(String idEmployee, String idProject, EmployeeProjectDTO employeeProjectDTO);
    void deleteEmployeeProject(String idEmployee, String idProject);

    //aggiunti questi due metodi per evitare validazioni e controlli già implementati in questo service perché
    //vanno in contrasto con i miei validator e controlli
    boolean  employeeExists(Employee employee);

    Employee addEmployeeFromFile(Employee employee);

}
