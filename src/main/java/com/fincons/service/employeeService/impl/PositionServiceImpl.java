package com.fincons.service.employeeService.impl;

import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.PositionMapper;
import com.fincons.repository.PositionRepository;
import com.fincons.service.employeeService.PositionService;
import com.fincons.utility.ValidateFields;
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

    @Override
    public Position getPositionByCode(String positionCode) {
        return validatePositionByCode(positionCode);
    }

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public Position createPosition(Position position) {

        checkForDuplicatePosition(position.getPositionCode(), position.getName());

        positionRepository.save(position);

        return position;
    }
    @Override
    public Position updatePositionByCode(String positionCode, Position position) {

        List<Position> positions = positionRepository.findAll();

        //Check if the specified CODE exists
        Position positionExisting = validatePositionByCode(positionCode);

        List<Position> positionsWithoutPositionCodeChosed = new ArrayList<>();

        for (Position p : positions) {
            if (!Objects.equals(p.getPositionCode(), positionCode)) {
                positionsWithoutPositionCodeChosed.add(p);
            }
        }

        positionExisting.setPositionCode(position.getPositionCode());
        positionExisting.setName(position.getName());
        positionExisting.setSalary(position.getSalary());

        if (positionsWithoutPositionCodeChosed.isEmpty()) {
            positionRepository.save(positionExisting);
        } else {
            for (Position p : positionsWithoutPositionCodeChosed) {
                if (p.getPositionCode().equals(positionExisting.getPositionCode())) {
                    throw new DuplicateException("This code: " + position.getPositionCode() + " is already taken");
                } else if (p.getName().equals(positionExisting.getName())) {
                    throw new DuplicateException("The name: " + position.getName() + " is already taken");
                }
            }
            positionRepository.save(positionExisting);
        }
        return positionExisting;
    }

    @Override
    public void deletePositionByCode(String positionCode) {
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

    public void validatePositionFields(PositionDTO positionDTO) {
        //If one field is true run Exception
        if (Strings.isEmpty(positionDTO.getPositionCode()) ||
                Strings.isEmpty(positionDTO.getName()) ||
                Objects.isNull(positionDTO.getSalary())) {
            throw new IllegalArgumentException("The fields of the Position can't be null or empty");
        }
    }

    private void checkForDuplicatePosition(String positionCode, String positionName) {
        Position positionByCode = positionRepository.findPositionByPositionCode(positionCode);
        Position positionByName = positionRepository.findPositionByName(positionName);
        if (positionByCode != null) {
            throw new DuplicateException("Position with the same code, already exists");
        }
        if (positionByName != null) {
            throw new DuplicateException("Position with the same name, already exists");

        }
    }
}
