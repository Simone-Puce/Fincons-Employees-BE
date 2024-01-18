package com.fincons.mapper;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDTO mapEmployeeToEmployeeDto(Employee employee){
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getGender(),
                employee.getBirthDate(),
                employee.getEmail(),
                employee.getStartDate(),
                employee.getEndDate(),
                employee.getDepartment(),
                employee.getPosition(),
                employee.getProjects(),
                employee.getCertificates(),
                employee.getFileList());
    }

    public Employee mapEmployeeDtoToEmployee(EmployeeDTO employeeDTO){
        return new Employee(
                employeeDTO.getId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getGender(),
                employeeDTO.getEmail(),
                employeeDTO.getBirthDate(),
                employeeDTO.getStartDate(),
                employeeDTO.getEndDate(),
                employeeDTO.getDepartment(),
                employeeDTO.getPosition(),
                employeeDTO.getProjects());
    }

/*
    public EmployeeDTO mapEmployeeTest(Employee employee){
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getGender(),
                employee.getEmail(),
                employee.getBirthDate(),
                employee.getStartDate(),
                employee.getEndDate());
    }
*/
}
