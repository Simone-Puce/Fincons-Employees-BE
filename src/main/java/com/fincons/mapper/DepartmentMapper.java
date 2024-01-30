package com.fincons.mapper;

import com.fincons.dto.DepartmentDTO;
import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    @Autowired
    ModelMapper modelMapper;

    public DepartmentDTO mapToDTO(Department department) {
        return modelMapper.map(department, DepartmentDTO.class);
    }
    public Department mapToEntity(DepartmentDTO departmentDTO){
        return modelMapper.map(departmentDTO, Department.class);
    }
}
