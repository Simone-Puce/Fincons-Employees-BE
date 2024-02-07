package com.fincons.controller;

import com.fincons.utility.GenericResponse;
import com.fincons.dto.PositionDTO;
import com.fincons.entity.Position;
import com.fincons.exception.DuplicateNameException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.PositionMapper;
import com.fincons.service.employeeService.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/company-employee-management")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionMapper modelMapperPosition;

    @GetMapping(value = "${position.find-position-by-id}")
    public ResponseEntity<GenericResponse<PositionDTO>> getPositionById(@RequestParam String positionId){

        try {
            Position position = positionService.getPositionById(positionId);
            PositionDTO positionDTO = modelMapperPosition.mapToDTO(position);
            GenericResponse<PositionDTO> response = GenericResponse.success(
                    positionDTO,
                    "Success: Found position with ID " + positionId + ".",
                    HttpStatus.OK);

            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        }
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND)
            );
        }
    }

    @GetMapping(value = "${position.list}")
    public ResponseEntity<GenericResponse<List<PositionDTO>>> getAllPositions() {

        List<Position> positions = positionService.getAllPositions();

        List<PositionDTO> positionDTOs = new ArrayList<>();
        for (Position position : positions) {
            PositionDTO positionDTO = modelMapperPosition.mapToDTO(position);
            positionDTOs.add(positionDTO);
        }
        GenericResponse<List<PositionDTO>> response = GenericResponse.success(
                positionDTOs,
                "Success: " + (positionDTOs.isEmpty() || positionDTOs.size() == 1 ? "Found " : "Founds ") + positionDTOs.size() +
                        (positionDTOs.isEmpty() || positionDTOs.size() == 1 ? " position" : " positions") + ".",
                HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "${position.create}")
    public ResponseEntity<Object> createPosition(@RequestBody PositionDTO positionDTO) {

        try {
            Position position = positionService.createPosition(positionDTO);
            PositionDTO positionDTO2 = modelMapperPosition.mapToDTO(position);

            GenericResponse<PositionDTO> response = GenericResponse.success(
                    positionDTO2,
                    "Success: Position with ID " + position.getId() + " has been successfully updated!",
                    HttpStatus.OK);
            return ResponseEntity.ok(response);

        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        } catch (DuplicateNameException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT
                    )
            );
        }
    }


    @PutMapping(value = "${position.update}")
    public ResponseEntity<Object> updatePositionById(@RequestParam String positionId, @RequestBody PositionDTO positionDTO) {
        try {
            Position position = positionService.updatePositionById(positionId, positionDTO);

            PositionDTO positionDTO2 = modelMapperPosition.mapToDTO(position);
            GenericResponse<PositionDTO> response = GenericResponse.success(
                    positionDTO2,
                    "Success: Position with ID " + positionId + " has been successfully updated!",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException rfe) {
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            rfe.getMessage(),
                            HttpStatus.NOT_FOUND
                    )
            );
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        } catch (DuplicateNameException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT
                    )
            );
        }
    }

    @DeleteMapping(value = "${position.delete}")
    public ResponseEntity<GenericResponse<PositionDTO>> deletePositionById(@RequestParam String positionId) {
        try {
            positionService.deletePositionById(positionId);
            GenericResponse<PositionDTO> response = GenericResponse.empty(
                    "Success: Position with ID " + positionId + " has been successfully deleted! ",
                    HttpStatus.OK);

            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        }
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND));
        }
    }

}
