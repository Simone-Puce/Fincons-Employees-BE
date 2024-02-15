package com.fincons.service.employeeService;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PositionService {

    Position getPositionByCode(String positionCode);
    List<Position> getAllPositions();
    Position createPosition(Position position);
    Position updatePositionByCode(String positionCode, Position position);
    void deletePositionByCode(String positionCode);
    void validatePositionFields(PositionDTO positionDTO);
}
