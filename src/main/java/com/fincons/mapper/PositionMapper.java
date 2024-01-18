package com.fincons.mapper;

import com.fincons.dto.DepartmentDTO;
import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.PositionDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PositionMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    public PositionDTO mapPosition(Position position){

        if(position.getEmployees() == null) {
            return new PositionDTO(
                    position.getName(),
                    position.getSalary());
        } else {
            List<EmployeeDTO> employeeDTOList = new ArrayList<>();

            position.getEmployees().forEach( employee ->
                    employeeDTOList.add(employeeMapper.mapEmployeeToEmployeeDtoWithoutObjects(employee))
            );
            return new PositionDTO(
                    position.getId(),
                    position.getName(),
                    position.getSalary(),
                    employeeDTOList);
        }
    }
}