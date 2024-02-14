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

    @Autowired
    private ModelMapper modelMapperStandard;

    public PositionDTO mapToDTO(Position position) {
        return modelMapperStandard.map(position, PositionDTO.class);
    }
    public Position mapToEntity(PositionDTO positionDTO){
        return modelMapperStandard.map(positionDTO, Position.class);
    }
}