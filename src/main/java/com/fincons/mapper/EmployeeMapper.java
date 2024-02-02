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

    @Autowired
    private ModelMapper modelMapperEmployee;

    @Autowired
    private ModelMapper modelMapperEmployeeWithFile;
    
    public EmployeeDTO mapToDTO(Employee employee) {
        return modelMapperEmployee.map(employee, EmployeeDTO.class);
    }

    public Employee mapToEntity(EmployeeDTO employeeDTO){
        return modelMapperEmployee.map(employeeDTO, Employee.class);
    }
    public EmployeeDTO mapToDTOWithFile(Employee employee) {
        return modelMapperEmployeeWithFile.map(employee, EmployeeDTO.class);
    }
    public Employee mapToEntityWithFile(EmployeeDTO employeeDTO){
        return modelMapperEmployeeWithFile.map(employeeDTO, Employee.class);
    }



}
