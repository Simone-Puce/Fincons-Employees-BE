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
    Employee createEmployee(EmployeeDTO employeeDTO);
    Employee updateEmployeeBySsn(String ssn, EmployeeDTO employeeDTO);
    void deleteEmployeeBySsn(String ssn);
    List<Project> findAllEmployeeProjects(String ssn);
    List<EmployeeProjectDTO> getAllEmployeeProject();
    EmployeeProjectDTO addEmployeeProject(String ssn, String idProject);
    EmployeeProjectDTO updateEmployeeProject(String ssn, String idProject, EmployeeProjectDTO employeeProjectDTO);
    void deleteEmployeeProject(String ssn, String idProject);

    //aggiunti questi due metodi per evitare validazioni e controlli già implementati in questo service perché
    //vanno in contrasto con i miei validator e controlli
    boolean  employeeExistsByEmail(Employee employee);

    public boolean employeeExistsBySsn(Employee employee);


    void deleteEmployee(Employee employee);

    Employee addEmployeeFromFile(Employee employee);

}
