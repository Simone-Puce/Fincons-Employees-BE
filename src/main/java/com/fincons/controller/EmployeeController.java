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
    public ResponseEntity<Object> getEmployeeById(@RequestParam String employeeId){
        return employeeService.getEmployeeById(employeeId);
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
    public ResponseEntity<Object> updateEmployeeById(@RequestParam String employeeId, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployeeById(employeeId, employeeDTO);
    }
    @DeleteMapping(value = "/delete")
    public  ResponseEntity<Object> deleteEmployeeById(@RequestParam String employeeId){
        return employeeService.deleteEmployeeById(employeeId);
    }

    @GetMapping(value = "/find/employee-project")
    public ResponseEntity<Object> getAllEmployeesProjects(@RequestParam String employeeId){
        return employeeService.findAllEmployeeProjects(employeeId);
    }
    @GetMapping(value = "/list/employee-project")
    public ResponseEntity<Object> getAllEmployeeProject(){
        return employeeService.getAllEmployeeProject();
    }
    @PostMapping(value= "/create/employee-project")
    public ResponseEntity<Object> createEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId) {
        return employeeService.addEmployeeProject(employeeId, projectId);
    }
    @PutMapping(value ="/update/employee-project")
    public ResponseEntity<Object> updateEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId, @RequestBody EmployeeProjectDTO employeeProjectDTO){
        return employeeService.updateEmployeeProject(employeeId, projectId, employeeProjectDTO);
    }
    @DeleteMapping(value= "/delete/employee-project")
    public ResponseEntity<Object> deleteEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId) {
        return employeeService.deleteEmployeeProject(employeeId, projectId);
    }



}

