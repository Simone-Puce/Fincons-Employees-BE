package com.fincons.service.employeeService;

import com.fincons.entity.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PositionService {

    ResponseEntity<Object> getPositionById(long id);
    ResponseEntity<Object> getAllPositions();
    ResponseEntity<Object> createPosition(Position position);
    ResponseEntity<Object> updatePositionById(long id, Position position);
    ResponseEntity<Object> deletePositionById(long id);

}
