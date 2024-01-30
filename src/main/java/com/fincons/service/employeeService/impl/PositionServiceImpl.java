package com.fincons.service.employeeService.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import com.fincons.exception.DuplicateNameException;
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
    public Position getPositionById(String positionId) {
        return validatePositionById(positionId);
    }

    @Override
    public List<Position> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        if(positions.isEmpty()){
            throw new IllegalArgumentException("There aren't Positions");
        }
        return positions;
    }

    @Override
    public Position createPosition(PositionDTO positionDTO) {

        //Contition for not have null attribute
        validatePositionFields(positionDTO);

        List<Position> positions = positionRepository.findAll();
        //Condition if there are positions with name same
        checkForDuplicatePosition(positionDTO, positions);

        Position position = modelMapperPosition.mapToEntity(positionDTO);

        positionRepository.save(position);

        return position;
    }

    @Override
    public Position updatePositionById(String positionId, PositionDTO positionDTO) {

        //Condition for not have null attributes
        validatePositionFields(positionDTO);

        List<Position> positions = positionRepository.findAll();

        //Check if the specified ID exists
        Position position = validatePositionById(positionId);


        position.setPositionId(positionId);
        position.setName(positionDTO.getName());
        position.setSalary(positionDTO.getSalary());


        List<Position> positionsWithoutPositionIdChosed = new ArrayList<>();

        for(Position p: positions){
            if(!Objects.equals(p.getPositionId(), positionId)){
                positionsWithoutPositionIdChosed.add(p);
            }
        }

        if(positionsWithoutPositionIdChosed.isEmpty()){
            positionRepository.save(position);
        }
        else {
            for (Position p : positionsWithoutPositionIdChosed) {
                if (p.getName().equals(position.getName()) &&
                        p.getSalary().equals(position.getSalary())
                ) {
                    throw new IllegalArgumentException("The position existing yet");
                } else {
                    positionRepository.save(position);
                    break;
                }
            }
        }

        return position;
    }

    @Override
    public void deletePositionById(String positionId) {

        Position position = validatePositionById(positionId);
        positionRepository.deleteById(position.getId());
    }

    public Position validatePositionById(String positionId) {
        Position position = positionRepository.findPositionByPositionId(positionId);

        if (position == null) {
            throw new ResourceNotFoundException("Position with ID: " + positionId + " not found");
        }
        return position;
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
                throw new DuplicateNameException("Position with the same name, already exists");
            }
        }
    }
}
