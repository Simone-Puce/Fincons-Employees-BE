package com.fincons.service.employeeService;

import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    ResponseEntity<Object> getEmployeeById(String idEmployee);
    ResponseEntity<Object> getEmployeeByEmail(String email);
    ResponseEntity<Object> getAllEmployees();
    ResponseEntity<Object> createEmployee(EmployeeDTO employeeDTO);
    ResponseEntity<Object> updateEmployeeById(String idEmployee, EmployeeDTO employeeDTO);
    ResponseEntity<Object> deleteEmployeeById(String idEmployee);
    ResponseEntity<Object> findAllEmployeeProjects(String idEmployee);
    ResponseEntity<Object> getAllEmployeeProject();
    ResponseEntity<Object> addEmployeeProject(String idEmployee, String idProject);
    ResponseEntity<Object> updateEmployeeProject(String idEmployee, String idProject, EmployeeProjectDTO employeeProjectDTO);
    ResponseEntity<Object> deleteEmployeeProject(String idEmployee, String idProject);
    ResponseEntity<Object> addEmployeeProject(long idEmployee, long idProject);
    ResponseEntity<Object> updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO);
    ResponseEntity<Object> deleteEmployeeProject(long idEmployee, long idProject);

    //aggiunti questi due metodi per evitare validazioni e controlli già implementati in questo service perché
    //vanno in contrasto con i miei validator e controlli
    boolean  employeeExists(Employee employee);

    Employee addEmployeeFromFile(Employee employee);

}
