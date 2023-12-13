package com.fincons.mapper;


import com.fincons.entity.Employee;
import com.fincons.model.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public Employee mapEmployeeDtoToEmployee(EmployeeDto employeeDto) {
        return new Employee(employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getEmail(), employeeDto.getImg(), employeeDto.getBirthDate(), employeeDto.getHireDate());
    }

    public EmployeeDto mapEmployeeToEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getImg(), employee.getAgency().getName(), employee.getBirthDate(), employee.getHireDate());
    }

    public List<EmployeeDto> mapEmployeeListToEmployeeDtoList(List<Employee> employeeList) {
        return employeeList.stream()
                .map(this::mapEmployeeToEmployeeDto)
                .collect(Collectors.toList());
    }
}
