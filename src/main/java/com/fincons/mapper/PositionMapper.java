package com.fincons.mapper;

import com.fincons.dto.PositionDTO;
import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import com.fincons.entity.Employee;
import com.fincons.entity.Position;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class PositionMapper {


    private ModelMapper modelMapperPosition;

    public PositionMapper(ModelMapper modelMapperPosition) {
        this.modelMapperPosition = modelMapperPosition;

        modelMapperPosition.addMappings(new PropertyMap<Employee, EmployeeDTO>() {
            @Override
            protected void configure() {
                skip(destination.getProjects());
            }
        });


    }
    public PositionDTO mapToDTO(Position position) {

        PositionDTO positionDTO = modelMapperPosition.map(position, PositionDTO.class);

        List<EmployeeDTO> employeeDTOs = position.getEmployees().stream()
                .map(employee -> modelMapperPosition.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());

        positionDTO.setEmployees(employeeDTOs);

        return positionDTO;
    }
    public Position mapToEntity(PositionDTO positionDTO){
        Position position = modelMapperPosition.map(positionDTO, Position.class);
        return position;
    }
}