package com.fincons.mapper;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {


    private final ModelMapper modelMapperEmployee;

    public EmployeeMapper(ModelMapper modelMapperEmployee) {
        this.modelMapperEmployee = modelMapperEmployee;
        modelMapperEmployee.getConfiguration().setAmbiguityIgnored(true);
        //modelMapperEmployee.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        modelMapperEmployee.addMappings(new PropertyMap<Project, ProjectDTO>() {
            @Override
            protected void configure() {
                skip(destination.getEmployees());
            }
        });

    }
    public EmployeeDTO mapToDTO(Employee employee) {

        EmployeeDTO employeeDTO = modelMapperEmployee.map(employee, EmployeeDTO.class);

        return employeeDTO;
    }

    public Employee mapToEntity(EmployeeDTO employeeDTO){

        Employee employee = modelMapperEmployee.map(employeeDTO, Employee.class);

        return employee;
    }
}
