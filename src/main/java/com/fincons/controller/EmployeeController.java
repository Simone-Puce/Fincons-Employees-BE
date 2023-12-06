package com.fincons.controller;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.service.EmployeeService;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ProjectService projectService;


    @GetMapping(value = "/find")
    public Employee getEmployeeById(@RequestParam long id){
        return employeeService.findById(id);
    }
    @GetMapping(value="/list")
    public List<Employee> getAllEmployees(){
        return employeeService.findAll();
    }
    @PostMapping(value = "/create")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }
    @GetMapping(value = "/find/employee-project")
    public List<Project> getAllEmployeesProjects(@RequestParam long id){
        return employeeService.findAllEmployeeProjects(id);
    }

    @PostMapping(value= "/create/employee-project")
    public Employee createEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject) {
        return employeeService.addEmployeeProject(idEmployee, idProject);
    }

    @DeleteMapping(value= "/delete/employee-project")
    public Employee deleteEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject) {
        return employeeService.deleteEmployeeProject(idEmployee, idProject);
    }


}

