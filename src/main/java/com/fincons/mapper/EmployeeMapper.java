package com.fincons.mapper;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDTO mapEmployee(Employee employee){
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
                employee.getCertificates());
    }
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

}
