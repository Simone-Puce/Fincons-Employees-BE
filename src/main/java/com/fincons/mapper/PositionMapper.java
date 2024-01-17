package com.fincons.mapper;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {
    public PositionDTO mapPosition(Position position){
        return new PositionDTO(
                position.getId(),
                position.getName(),
                position.getSalary());
    }
}
