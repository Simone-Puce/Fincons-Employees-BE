package com.fincons.controller;

import com.fincons.entity.Employee;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.dto.EmployeeDto;
import com.fincons.service.employee.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService IEmployeeService;
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/all")
    public List<EmployeeDto> getAllEmployees() {
        return IEmployeeService.getAllEmployee();
    }

    @GetMapping("/find/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        return IEmployeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeDto employeeDto) throws Exception {
        return IEmployeeService.createEmployee(employeeMapper.mapEmployeeDtoToEmployee(employeeDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return IEmployeeService.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        return IEmployeeService.deleteEmployee(id);
    }


}