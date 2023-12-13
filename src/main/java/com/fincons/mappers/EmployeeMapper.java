package com.fincons.mappers;

import com.fincons.entities.Employee;
import com.fincons.models.EmployeeDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EmployeeMapper {


    public Employee mapEmployeeDtoToEmployee(EmployeeDTO employeeDto) {
        return new Employee(employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getEmail());
    }

    public EmployeeDTO mapEmployeetoEmployeeDto(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getFileList().toString());
    }


    public List<EmployeeDTO> mapEmployeeListToEmployeeDtoList(List<Employee> employeeList) {
        return employeeList.stream().map(this::mapEmployeetoEmployeeDto).toList();
    }
}
