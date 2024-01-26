package com.fincons.controller;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import com.fincons.service.employeeService.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company-employee-management")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping(value = "${position.find-position-by-id}")
    public ResponseEntity<Object> getPositionById(@RequestParam String positionId){
        return positionService.getPositionById(positionId);
    }
    @GetMapping(value="${position.list}")
    public ResponseEntity<Object> getAllPositions(){
        return positionService.getAllPositions();
    }
    @PostMapping(value = "${position.create}")
    public ResponseEntity<Object> createPosition(@RequestBody PositionDTO positionDTO){
        return positionService.createPosition(positionDTO);
    }
    @PutMapping(value = "${position.update}")
    public ResponseEntity<Object> updatePositionById(@RequestParam String positionId, @RequestBody PositionDTO positionDTO) {
        return positionService.updatePositionById(positionId, positionDTO);
    }
    @DeleteMapping(value = "${position.delete}")
    public ResponseEntity<Object> deletePositionById(@RequestParam String positionId){
        return positionService.deletePositionById(positionId);
    }

    
    
}
