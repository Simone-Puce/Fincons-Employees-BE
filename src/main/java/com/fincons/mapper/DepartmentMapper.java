package com.fincons.mapper;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    @Autowired
    private ModelMapper modelMapperStandard;

    public DepartmentDTO mapToDTO(Department department) {
        return modelMapperStandard.map(department, DepartmentDTO.class);
    }
    public Department mapToEntity(DepartmentDTO departmentDTO){
        return modelMapperStandard.map(departmentDTO, Department.class);
    }
}
