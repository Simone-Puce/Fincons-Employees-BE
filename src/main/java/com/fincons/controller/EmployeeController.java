package com.fincons.controller;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.service.EmployeeService;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${employee.uri}")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ProjectService projectService;


    @GetMapping(value = "/find")
    public ResponseEntity<Object> getEmployeeById(@RequestParam long id){
        return employeeService.findById(id);
    }
    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllEmployees(){
        return employeeService.findAll();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee){
        return employeeService.save(employee);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateEmployeeById(@RequestParam long id, @RequestBody Employee employee){
        return employeeService.update(id, employee);
    }
    @DeleteMapping(value = "/delete")
    public  ResponseEntity<Object> deleteEmployeeById(@RequestParam long id){
        return employeeService.deleteById(id);
    }

    @GetMapping(value = "/find/employee-project")
    public ResponseEntity<Object> getAllEmployeesProjects(@RequestParam long id){
        return employeeService.findAllEmployeeProjects(id);
    }
    @GetMapping(value = "/list/employee-project")
    public ResponseEntity<Object> getAllEmployeeProject(){
        return employeeService.getAllEmployeeProject();
    }
    @PostMapping(value= "/create/employee-project")
    public ResponseEntity<Object> createEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject) {
        return employeeService.addEmployeeProject(idEmployee, idProject);
    }
    @PutMapping(value ="/update/employee-project")
    public ResponseEntity<Object> updateEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject, @RequestBody EmployeeProjectDTO employeeProjectDTO){
        return employeeService.updateEmployeeProject(idEmployee, idProject, employeeProjectDTO);
    }
    @DeleteMapping(value= "/delete/employee-project")
    public ResponseEntity<Object> deleteEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject) {
        return employeeService.deleteEmployeeProject(idEmployee, idProject);
    }



}

