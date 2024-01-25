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


    private ModelMapper modelMapperDepartment;

    public DepartmentMapper(ModelMapper modelMapperDepartment) {
        this.modelMapperDepartment = modelMapperDepartment;

        modelMapperDepartment.addMappings(new PropertyMap<Employee, EmployeeDTO>() {
            @Override
            protected void configure() {
                skip(destination.getProjects());

            }
        });


    }
    public DepartmentDTO mapToDTO(Department department) {

        DepartmentDTO departmentDTO = modelMapperDepartment.map(department, DepartmentDTO.class);


        List<EmployeeDTO> employeeDTOs = department.getEmployees().stream()
                .map(employee -> modelMapperDepartment.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());

        departmentDTO.setEmployees(employeeDTOs);



        return departmentDTO;
    }
    public Department mapToEntity(DepartmentDTO departmentDTO){
        Department department = modelMapperDepartment.map(departmentDTO, Department.class);
        return department;
    }
}
