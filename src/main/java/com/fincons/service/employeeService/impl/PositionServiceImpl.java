package com.fincons.service.employeeService.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.PositionMapper;
import com.fincons.repository.PositionRepository;
import com.fincons.service.employeeService.PositionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public ResponseEntity<Object> getPositionById(long id) {

        Position existingPosition = validatePositionById(id);
        PositionDTO positionDTO = positionMapper.mapPosition(existingPosition);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found position with ID " + id + ".",
                (HttpStatus.OK),
                positionDTO);
    }

    @Override
    public ResponseEntity<Object> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        List<PositionDTO> newListPosition = new ArrayList<>();
        //Check if the list of department is empty
        for (Position position : positions){
            if(position != null){
                PositionDTO positionDTO = positionMapper.mapPosition(position);
                newListPosition.add(positionDTO);
            } else{
                throw new IllegalArgumentException("There aren't Positions");
            }
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found " + newListPosition.size() +
                        (newListPosition.size() == 1 ? " position" : " positions") + " in the list.",
                (HttpStatus.OK),
                newListPosition);
    }

    @Override
    public ResponseEntity<Object> createPosition(Position position) {
        //Contition for not have null attribute
        validatePositionFields(position);

        List<Position> positions = positionRepository.findAll();
        //Condition if there are positions with name same
        checkForDuplicatePosition(position, positions);

        PositionDTO positionDTO = positionMapper.mapPosition(position);
        positionRepository.save(position);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Position with ID "+ position.getId() +" has been successfully updated!",
                (HttpStatus.OK), positionDTO);
    }

    @Override
    public ResponseEntity<Object> updatePositionById(long id, Position position) throws Exception {

        //Condition for not have null attributes
        validatePositionFields(position);

        PositionDTO positionDTO;
        //Check if the specified ID exists
        Position existingPosition = validatePositionById(id);

        List<Position> positions = positionRepository.findAll();


        existingPosition.setId(id);
        existingPosition.setName(position.getName());
        existingPosition.setSalary(position.getSalary());


        List<Position> positionsWithoutPositionIdChosed = new ArrayList<>();

        for(Position p: positions){
            if(p.getId() != id){
                positionsWithoutPositionIdChosed.add(p);
            }
        }

        if(positionsWithoutPositionIdChosed.isEmpty()){
            positionRepository.save(existingPosition);
        }
        for (Position p: positionsWithoutPositionIdChosed){
            if(p.getName().equals(existingPosition.getName()) &&
                    p.getSalary().equals(existingPosition.getSalary())
            ){
                throw new Exception("The position existing yet");
            }
            else {
                positionRepository.save(existingPosition);
            }
        }

        positionDTO = positionMapper.mapPosition(position);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Position with ID "+ id +" has been successfully updated!",
                (HttpStatus.OK),
                positionDTO);
    }

    @Override
    public ResponseEntity<Object> deletePositionById(long id) {
        getPositionById(id);
        positionRepository.deleteById(id);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Position with ID "+ id +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    private Position validatePositionById(long id) {
        Position existingPosition = positionRepository.findById(id);

        if (existingPosition == null) {
            throw new ResourceNotFoundException("Position with ID: " + id + " not found");
        }
        return existingPosition;
    }

    private void validatePositionFields(Position position){
        //If one field is true run Exception
        if (Strings.isEmpty(position.getName()) ||
                Objects.isNull(position.getSalary())) {
            throw new IllegalArgumentException("The fields of the Position can't be null or empty");
        }
    }

    private void checkForDuplicatePosition(Position position, List<Position> positions){
        for(Position position1 : positions){
            if(position1.getName().equals(position.getName())){
                throw new IllegalArgumentException("Position with the same name, already exists");
            }
        }
    }
}
