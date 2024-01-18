package com.fincons.service.employeeService;

import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    ResponseEntity<Object> getEmployeeById(long id);
    ResponseEntity<Object> getEmployeeByEmail(String email);
    ResponseEntity<Object> getAllEmployees();
    ResponseEntity<Object> createEmployee(Employee employee);
    ResponseEntity<Object> updateEmployeeById(long id, Employee employee);
    ResponseEntity<Object> deleteEmployeeById(long id);
    ResponseEntity<Object> findAllEmployeeProjects(long id);
    ResponseEntity<Object> getAllEmployeeProject();
    ResponseEntity<Object> addEmployeeProject(long idEmployee, long idProject);
    ResponseEntity<Object> updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO);
    ResponseEntity<Object> deleteEmployeeProject(long idEmployee, long idProject);

    //aggiunti questi due metodi per evitare validazioni e controlli già implementati in questo service perché
    //vanno in contrasto con i miei validator e controlli
    boolean  employeeExists(Employee employee);

    Employee addEmployeeFromFile(Employee employee);

}
