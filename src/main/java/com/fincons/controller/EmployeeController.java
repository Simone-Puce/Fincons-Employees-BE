package com.fincons.controller;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.employeeService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${employee.uri}")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ProjectService projectService;

    @GetMapping(value = "/find-by-id")
    public ResponseEntity<Object> getEmployeeById(@RequestParam String idEmployee){
        return employeeService.getEmployeeById(idEmployee);
    }
    @GetMapping(value = "/find-by-email")
    public ResponseEntity<Object> getDepartmentByEmail(@RequestParam String email){
        return employeeService.getEmployeeByEmail(email);
    }
    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.createEmployee(employeeDTO);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateEmployeeById(@RequestParam String idEmployee, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployeeById(idEmployee, employeeDTO);
    }
    @DeleteMapping(value = "/delete")
    public  ResponseEntity<Object> deleteEmployeeById(@RequestParam String idEmployee){
        return employeeService.deleteEmployeeById(idEmployee);
    }

    @GetMapping(value = "/find/employee-project")
    public ResponseEntity<Object> getAllEmployeesProjects(@RequestParam String idEmployee){
        return employeeService.findAllEmployeeProjects(idEmployee);
    }
    @GetMapping(value = "/list/employee-project")
    public ResponseEntity<Object> getAllEmployeeProject(){
        return employeeService.getAllEmployeeProject();
    }
    @PostMapping(value= "/create/employee-project")
    public ResponseEntity<Object> createEmployeeProject(@RequestParam String idEmployee, @RequestParam String idProject) {
        return employeeService.addEmployeeProject(idEmployee, idProject);
    }
    @PutMapping(value ="/update/employee-project")
    public ResponseEntity<Object> updateEmployeeProject(@RequestParam String idEmployee, @RequestParam String idProject, @RequestBody EmployeeProjectDTO employeeProjectDTO){
        return employeeService.updateEmployeeProject(idEmployee, idProject, employeeProjectDTO);
    }
    @DeleteMapping(value= "/delete/employee-project")
    public ResponseEntity<Object> deleteEmployeeProject(@RequestParam String idEmployee, @RequestParam String idProject) {
        return employeeService.deleteEmployeeProject(idEmployee, idProject);
    }



}

