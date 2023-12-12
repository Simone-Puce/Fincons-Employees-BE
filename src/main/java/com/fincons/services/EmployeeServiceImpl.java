package com.fincons.services;


import com.fincons.entities.Employee;
import com.fincons.exceptions.ResourceNotFoundException;
import com.fincons.mappers.EmployeeMapper;
import com.fincons.models.EmployeeDTO;
import com.fincons.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeServiceApi {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;


    @Override
    public EmployeeDTO createEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee createEmployee(EmployeeDTO employeeDto){
        Employee newEmployee =  employeeMapper.mapEmployeeDtoToEmployee(employeeDto);
        return employeeRepository.save(newEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeMapper.mapEmployeeListToEmployeeDtoList(employeeList);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employee -> employeeMapper.mapEmployeetoEmployeeDto(employee)).orElse(null);

    }

    @Override
    public Employee addEmployee(EmployeeDTO newEmployee) {
        Employee employee = employeeMapper.mapEmployeeDtoToEmployee(newEmployee);
        return employeeRepository.save(employee);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exixst with id: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee don't exist with id:" + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Override
    public Employee createEmployee() {
        return null;
    }
}

