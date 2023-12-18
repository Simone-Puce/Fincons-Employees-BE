package com.fincons.mapper;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public DepartmentDTO mapDepartment(Department department){
        return new DepartmentDTO(
                department.getName(),
                department.getAddress(),
                department.getCity());
    }
}
