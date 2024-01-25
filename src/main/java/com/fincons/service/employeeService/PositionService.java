package com.fincons.service.employeeService;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PositionService {

    ResponseEntity<Object> getPositionById(String idPosition);
    ResponseEntity<Object> getAllPositions();
    ResponseEntity<Object> createPosition(PositionDTO positionDTO);
    ResponseEntity<Object> updatePositionById(String idPosition, PositionDTO positionDTO);
    ResponseEntity<Object> deletePositionById(String idPosition);

}
