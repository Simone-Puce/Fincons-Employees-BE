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
    private PositionMapper modelMapperPosition;

    @Override
    public ResponseEntity<Object> getPositionById(String idPosition) {

        Position existingPosition = validatePositionById(idPosition);
        PositionDTO positionDTO = modelMapperPosition.mapToDTO(existingPosition);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found position with ID " + idPosition + ".",
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
                PositionDTO positionDTO = modelMapperPosition.mapToDTO(position);
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
    public ResponseEntity<Object> createPosition(PositionDTO positionDTO) {

        //Contition for not have null attribute
        validatePositionFields(positionDTO);

        List<Position> positions = positionRepository.findAll();
        //Condition if there are positions with name same
        checkForDuplicatePosition(positionDTO, positions);

        Position position = modelMapperPosition.mapToEntity(positionDTO);

        positionRepository.save(position);

        positionDTO.setPositionId(position.getPositionId());

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Position with ID "+ position.getId() +" has been successfully updated!",
                (HttpStatus.OK), positionDTO);
    }

    @Override
    public ResponseEntity<Object> updatePositionById(String idPosition, PositionDTO positionDTO) {

        //Condition for not have null attributes
        validatePositionFields(positionDTO);

        List<Position> positions = positionRepository.findAll();

        //Check if the specified ID exists
        Position existingPosition = validatePositionById(idPosition);


        existingPosition.setPositionId(idPosition);
        existingPosition.setName(positionDTO.getName());
        existingPosition.setSalary(positionDTO.getSalary());


        List<Position> positionsWithoutPositionIdChosed = new ArrayList<>();

        for(Position p: positions){
            if(!Objects.equals(p.getPositionId(), idPosition)){
                positionsWithoutPositionIdChosed.add(p);
            }
        }

        if(positionsWithoutPositionIdChosed.isEmpty()){
            positionRepository.save(existingPosition);
            positionDTO.setPositionId(existingPosition.getPositionId());
        }
        else {
            for (Position p : positionsWithoutPositionIdChosed) {
                if (p.getName().equals(existingPosition.getName()) &&
                        p.getSalary().equals(existingPosition.getSalary())
                ) {
                    throw new IllegalArgumentException("The position existing yet");
                } else {
                    positionRepository.save(existingPosition);
                    positionDTO.setPositionId(existingPosition.getPositionId());
                }
            }
        }

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Position with ID "+ idPosition +" has been successfully updated!",
                (HttpStatus.OK),
                positionDTO);
    }

    @Override
    public ResponseEntity<Object> deletePositionById(String idPosition) {

        Position position = validatePositionById(idPosition);
        positionRepository.deleteById(position.getId());
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Position with ID "+ idPosition +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    private Position validatePositionById(String idPosition) {
        Position existingPosition = positionRepository.findByPositionId(idPosition);

        if (existingPosition == null) {
            throw new ResourceNotFoundException("Position with ID: " + idPosition + " not found");
        }
        return existingPosition;
    }

    private void validatePositionFields(PositionDTO positionDTO){
        //If one field is true run Exception
        if (Strings.isEmpty(positionDTO.getName()) ||
                Objects.isNull(positionDTO.getSalary())) {
            throw new IllegalArgumentException("The fields of the Position can't be null or empty");
        }
    }

    private void checkForDuplicatePosition(PositionDTO positionDTO, List<Position> positions){
        for(Position position1 : positions){
            if(position1.getName().equals(positionDTO.getName())){
                throw new IllegalArgumentException("Position with the same name, already exists");
            }
        }
    }
}
