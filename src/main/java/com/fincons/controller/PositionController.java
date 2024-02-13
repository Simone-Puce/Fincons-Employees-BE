package com.fincons.controller;

import com.fincons.entity.Position;
import com.fincons.service.employeeService.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/company-employee-management")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping(value = "${position.find-position-by-id}")
    public ResponseEntity<Object> getPositionById(@RequestParam long id){
        return positionService.getPositionById(id);
    }
    @GetMapping(value="${position.list}")
    public ResponseEntity<Object> getAllPositions(){
        return positionService.getAllPositions();
    }
    @PostMapping(value = "${position.create}")
    public ResponseEntity<Object> createPosition(@RequestBody Position position){
        return positionService.createPosition(position);
    }
    @PutMapping(value = "${position.update}")
    public ResponseEntity<Object> updatePositionById(@RequestParam long id, @RequestBody Position position) throws Exception {
        return positionService.updatePositionById(id, position);
    }
    @DeleteMapping(value = "${position.delete}")
    public ResponseEntity<Object> deletePositionById(@RequestParam long id){
        return positionService.deletePositionById(id);
    }

    
    
}
