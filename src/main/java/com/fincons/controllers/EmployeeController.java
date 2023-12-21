package com.fincons.controllers;

import com.fincons.entities.Employee;
import com.fincons.models.EmployeeDTO;
import com.fincons.services.EmployeeServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    EmployeeServiceApi employeeServiceApi;

    //get all employees
    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeServiceApi.getAllEmployees();
    }


    // create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeServiceApi.createEmployee(employeeDTO);
    }

    //get employee by id rest api
    @GetMapping("/employees/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeServiceApi.getEmployeeById(id);
    }

    //update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeServiceApi.updateEmployee(id, employeeDetails);
    }

    //delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        return employeeServiceApi.deleteEmployee(id);
    }


}