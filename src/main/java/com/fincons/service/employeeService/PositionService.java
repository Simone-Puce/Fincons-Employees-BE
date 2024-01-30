package com.fincons.service.employeeService;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionService {

    Position getPositionById(String positionId);
    List<Position> getAllPositions();
    Position createPosition(PositionDTO positionDTO);
    Position updatePositionById(String positionId, PositionDTO positionDTO);
    void deletePositionById(String positionId);

}
