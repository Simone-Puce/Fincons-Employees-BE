package com.fincons.controller;


import com.fincons.utility.GenericResponse;
import com.fincons.dto.DepartmentDTO;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.exception.DuplicateNameException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.service.employeeService.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/company-employee-management")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapper modelMapperDepartment;

    @GetMapping(value = "${department.find-by-id}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> getDepartmentById(@RequestParam String departmentId){

        try {
            Department department = departmentService.getDepartmentById(departmentId);
            DepartmentDTO departmentDTO = modelMapperDepartment.mapToDTO(department);

            GenericResponse<DepartmentDTO> response = GenericResponse.success(
                    departmentDTO,
                    "Success: Found position with ID " + departmentId + ".",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        }
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value())
            );
        }
    }

    @GetMapping(value = "${department.list}")
    public ResponseEntity<GenericResponse<List<DepartmentDTO>>> getAllDepartment() {

        List<Department> departments = departmentService.getAllDepartment();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();
        for (Department department : departments) {
            DepartmentDTO departmentDTO = modelMapperDepartment.mapToDTO(department);
            departmentDTOs.add(departmentDTO);
        }
        GenericResponse<List<DepartmentDTO>> response = GenericResponse.success(
                departmentDTOs,
                "Success:" + (departmentDTOs.isEmpty() || departmentDTOs.size() == 1 ? " Found " : " Founds ") + departmentDTOs.size() +
                        (departmentDTOs.isEmpty() || departmentDTOs.size() == 1 ? " department" : " departments") + ".",
                HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "${department.create}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            Department department = departmentService.createDepartment(departmentDTO);

            DepartmentDTO departmentDTO2 = modelMapperDepartment.mapToDTO(department);

            GenericResponse<DepartmentDTO> response = GenericResponse.success(
                    departmentDTO2,
                    "Success: Department with ID " + department.getDepartmentId() + " has been successfully updated!",
                    HttpStatus.OK.value());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        } catch (DuplicateNameException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT.value()
                    )
            );
        }
    }

    @PutMapping(value = "${department.update}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> updateDepartmentById(@RequestParam String departmentId, @RequestBody DepartmentDTO departmentDTO) {
        try {
            Department department = departmentService.updateDepartmentById(departmentId, departmentDTO);

            DepartmentDTO departmentDTO2 = modelMapperDepartment.mapToDTO(department);

            GenericResponse<DepartmentDTO> response = GenericResponse.success(
                    departmentDTO2,
                    "Success: Department with ID " + departmentId + " has been successfully updated!",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException rfe) {
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            rfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()
                    )
            );
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        } catch (DuplicateNameException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT.value()
                    )
            );
        }
    }

    @DeleteMapping(value = "${department.delete}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> deleteDepartmentById(@RequestParam String departmentId) {
        try {
            departmentService.deleteDepartmentById(departmentId);
            GenericResponse<DepartmentDTO> response = GenericResponse.empty(
                    "Success: Department with ID " + departmentId + " has been successfully deleted! ",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        }
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping(value = "${department.find-employee-by-iddepartment}")
    public ResponseEntity<GenericResponse<List<EmployeeDepartmentDTO>>> getDepartmentFindEmployee(@RequestParam String departmentId) {

        try {
            List<EmployeeDepartmentDTO> employeeDepartmentDTOList = departmentService.getDepartmentEmployeesFindByIdDepartment(departmentId);
            GenericResponse<List<EmployeeDepartmentDTO>> response = GenericResponse.success(
                    employeeDepartmentDTOList,
                    "Success: This Department has " + employeeDepartmentDTOList.size() +
                            (employeeDepartmentDTOList.size() == 1 ? " employee." : " employees."),
                    HttpStatus.OK.value());

            return ResponseEntity.ok(response);

        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        }
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()
                    )
            );
        }
    }
}
