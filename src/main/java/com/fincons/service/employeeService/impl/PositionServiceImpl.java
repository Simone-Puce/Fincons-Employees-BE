package com.fincons.service.employeeService.impl;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.PositionMapper;
import com.fincons.repository.PositionRepository;
import com.fincons.service.employeeService.PositionService;
import com.fincons.utility.ValidateSingleField;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Position getPositionByCode(String positionCode) {
        ValidateSingleField.validateSingleField(positionCode);
        return validatePositionByCode(positionCode);
    }

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
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
    public Position updatePositionByCode(String positionCode, PositionDTO positionDTO) {

        //Condition for not have null attributes
        ValidateSingleField.validateSingleField(positionCode);
        validatePositionFields(positionDTO);

        List<Position> positions = positionRepository.findAll();

        //Check if the specified CODE exists
        Position position = validatePositionByCode(positionCode);

        List<Position> positionsWithoutPositionCodeChosed = new ArrayList<>();

        for(Position p: positions){
            if(!Objects.equals(p.getPositionCode(), positionCode)){
                positionsWithoutPositionCodeChosed.add(p);
            }
        }

        position.setPositionCode(positionDTO.getPositionCode());
        position.setName(positionDTO.getName());
        position.setSalary(positionDTO.getSalary());


        if(positionsWithoutPositionCodeChosed.isEmpty()){
            positionRepository.save(position);
        }
        else {
            for (Position p : positionsWithoutPositionCodeChosed) {
                if(p.getPositionCode().equals(position.getPositionCode())){
                    throw new DuplicateException("This code: " + positionDTO.getPositionCode() + " is already taken");
                }
                else if (p.getName().equals(position.getName()) &&
                        p.getSalary().equals(position.getSalary())
                ) {
                    throw new DuplicateException("The name with this salary is already taken");
                }
            }
            positionRepository.save(position);
        }
        return position;
    }

    @Override
    public void deletePositionByCode(String positionCode) {

        ValidateSingleField.validateSingleField(positionCode);
        Position position = validatePositionByCode(positionCode);
        positionRepository.deleteById(position.getId());
    }

    public Position validatePositionByCode(String positionCode) {
        Position position = positionRepository.findPositionByPositionCode(positionCode);

        if (position == null) {
            throw new ResourceNotFoundException("Position with code: " + positionCode + " not found");
        }
        return position;
    }

    private void validatePositionFields(PositionDTO positionDTO){
        //If one field is true run Exception
        if (Strings.isEmpty(positionDTO.getPositionCode()) ||
                Strings.isEmpty(positionDTO.getName()) ||
                Objects.isNull(positionDTO.getSalary())) {
            throw new IllegalArgumentException("The fields of the Position can't be null or empty");
        }
    }

    private void checkForDuplicatePosition(PositionDTO positionDTO, List<Position> positions){
        for(Position position1 : positions){
            if (position1.getPositionCode().equals(positionDTO.getPositionCode())){
                throw new DuplicateException("Position with the same code, already exists");
            }
            if(position1.getName().equals(positionDTO.getName()) &&
                    position1.getSalary().equals(positionDTO.getSalary()) ){
                throw new DuplicateException("Position with the same name and salary, already exists");
            }
        }
    }
}
