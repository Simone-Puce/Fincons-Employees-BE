package com.fincons.mapper;

import com.fincons.dto.DepartmentDTO;
import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    public DepartmentDTO mapDepartment(Department department){

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        department.getEmployees().forEach( employee ->
                employeeDTOList.add(employeeMapper.mapEmployee(employee))
        );
        return new DepartmentDTO(
                department.getName(),
                department.getAddress(),
                department.getCity(),
                employeeDTOList);
    }
    public DepartmentDTO mapDepartmentWithoutEmployee(Department department){
        return new DepartmentDTO(
                department.getName(),
                department.getAddress(),
                department.getCity()
        );
    }
}
