package com.fincons.controller;

import com.fincons.entity.Position;
import com.fincons.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${position.uri}")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping(value = "/find")
    public ResponseEntity<Object> getPositionById(@RequestParam long id){
        return positionService.getPositionById(id);
    }
    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllPositions(){
        return positionService.getAllPositions();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createPosition(@RequestBody Position position){
        return positionService.createPosition(position);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updatePositionById(@RequestParam long id, @RequestBody Position position){
        return positionService.updatePositionById(id, position);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deletePositionById(@RequestParam long id){
        return positionService.deletePositionById(id);
    }

    
    
}
