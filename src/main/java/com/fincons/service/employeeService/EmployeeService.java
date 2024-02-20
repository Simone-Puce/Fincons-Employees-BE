package com.fincons.service.employeeService;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.entity.Project;
import java.util.List;

public interface EmployeeService {

    Employee getEmployeeBySsn(String ssn);
    Employee getEmployeeByEmail(String email);
    List<Employee> getAllEmployees();
    Employee createEmployee(Employee employee);
    Employee updateEmployeeBySsn(String ssn, Employee employee);
    void deleteEmployeeBySsn(String ssn);
    List<Project> findAllEmployeeProjects(String ssn);
    List<EmployeeProjectDTO> getAllEmployeeProject();
    EmployeeProjectDTO addEmployeeProject(String ssn, String idProject);
    EmployeeProjectDTO updateEmployeeProject(String ssn, String idProject, EmployeeProjectDTO employeeProjectDTO);
    void deleteEmployeeProject(String ssn, String idProject);

    void validateEmployeeFields(EmployeeDTO employeeDTO);

    void validateGender(String gender);

    //added these two methods to avoid validations and checks already implemented in
    // this service because they conflict with my validators and checks
    boolean  employeeExists(Employee employee);

    void deleteEmployee(Employee employee);

    Employee addEmployeeFromFile(Employee employee);


}
