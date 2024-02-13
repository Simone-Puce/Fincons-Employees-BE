package com.fincons.service.employeeService;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PositionService {

    Position getPositionByCode(String positionCode);
    List<Position> getAllPositions();
    Position createPosition(PositionDTO positionDTO);
    Position updatePositionByCode(String positionCode, PositionDTO positionDTO);
    void deletePositionByCode(String positionCode);

}
