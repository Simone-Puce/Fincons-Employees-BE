package com.fincons.service.employee;


import com.fincons.entity.Employee;
import com.fincons.exception.PersonalException;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.dto.EmployeeDto;
import com.fincons.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.mapEmployeeListToEmployeeDtoList(employees);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employee -> employeeMapper.mapEmployeeToEmployeeDto(employee)).orElse(null);
    }


    @Override
    public Employee createEmployee(Employee employee) throws RuntimeException {
        String email = employee.getEmail();

        Optional<Employee> existingEmployeeWithEmail = employeeRepository.findByEmail(email);
        if (existingEmployeeWithEmail.isPresent()) {
            String errorMessage = "An employee with this email already exists.";
            throw new IllegalArgumentException(errorMessage);
        }
        return employeeRepository.save(employee);
    }


    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();

            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setImg(employee.getImg());
            existingEmployee.setHireDate(employee.getHireDate());
            existingEmployee.setBirthDate(employee.getBirthDate());

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new PersonalException("Employee non trovato con id: " + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
