package com.fincons.controller;

import com.fincons.entity.Employee;
import com.fincons.model.EmployeeDto;
import com.fincons.service.employee.EmployeeServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceApi employeeServiceApi;

    @GetMapping("/all")
    public List<EmployeeDto> getAllEmployees() {
        return employeeServiceApi.getAllEmployee();
    }

    @GetMapping("/find/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        return employeeServiceApi.getEmployeeById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeDto employeeDto) throws Exception {
        return employeeServiceApi.createEmployee(employeeDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeServiceApi.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) {
        employeeServiceApi.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }


}