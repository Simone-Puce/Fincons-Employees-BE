package com.fincons.mapper;

import com.fincons.dto.DepartmentDTO;
import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    public DepartmentDTO mapDepartment(Department department) {
        if (department.getEmployees() == null) {
            return new DepartmentDTO(
                        department.getName(),
                        department.getAddress(),
                        department.getCity()
            );

        } else {
            List<EmployeeDTO> employeeDTOList = new ArrayList<>();

            department.getEmployees().forEach(employee ->
                    employeeDTOList.add(employeeMapper.mapEmployeeToEmployeeDtoWithoutObjects(employee))
            );
            return new DepartmentDTO(
                    department.getId(),
                    department.getName(),
                    department.getAddress(),
                    department.getCity(),
                    employeeDTOList);
        }

    }
}
