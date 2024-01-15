package com.fincons.mapper;


import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProjectMapper {
    public EmployeeProjectDTO mapEmployeeProject(Employee employee, Project project){
        return new EmployeeProjectDTO(
                employee.getLastName(),
                employee.getId(),
                project.getName(),
                project.getId()
                );
    }
}
