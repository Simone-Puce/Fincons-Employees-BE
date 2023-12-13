package com.fincons.controller;


import com.fincons.model.EmployeeDto;
import com.fincons.service.EmployeeServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeServiceApi employeeServiceApi;

    @Autowired
    public EmployeeController(EmployeeServiceApi employeeServiceApi) {
        this.employeeServiceApi = employeeServiceApi;
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeServiceApi.getAllEmployee();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDto> employee = employeeServiceApi.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) throws Exception {
        Optional<EmployeeDto> createdEmployee = employeeServiceApi.createEmployee(employeeDto);
        System.out.println(employeeDto);
        return createdEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        Optional<EmployeeDto> updatedEmployee = employeeServiceApi.updateEmployee(id, employeeDto);
        return updatedEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeServiceApi.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}