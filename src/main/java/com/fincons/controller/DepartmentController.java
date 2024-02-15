package com.fincons.controller;


import com.fincons.service.employeeService.impl.DepartmentServiceImpl;
import com.fincons.utility.GenericResponse;
import com.fincons.dto.DepartmentDTO;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.service.employeeService.DepartmentService;
import com.fincons.utility.ValidateFields;
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
    public DepartmentMapper modelMapperDepartment;




    public DepartmentController(DepartmentService departmentService, DepartmentMapper modelMapperDepartment) {
        this.departmentService = departmentService;
        this.modelMapperDepartment = modelMapperDepartment;
    }


    @GetMapping(value = "${department.find-by-code}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> getDepartmentByCode(@RequestParam String departmentCode) {

        try {
            ValidateFields.validateSingleField(departmentCode);
            Department department = departmentService.getDepartmentByCode(departmentCode);
            DepartmentDTO departmentDTO = modelMapperDepartment.mapToDTO(department);

            GenericResponse<DepartmentDTO> response = GenericResponse.success(
                    departmentDTO,
                    "Success: Found department with code: " + departmentCode + ".",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND)
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
                HttpStatus.OK);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "${department.create}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            departmentService.validateDepartmentFields(departmentDTO);

            Department departmentMapped = modelMapperDepartment.mapToEntity(departmentDTO);

            Department department = departmentService.createDepartment(departmentMapped);

            DepartmentDTO departmentDTO2 = modelMapperDepartment.mapToDTO(department);

            GenericResponse<DepartmentDTO> response = GenericResponse.success(
                    departmentDTO2,
                    "Success: Department with code: " + department.getDepartmentCode() + " has been successfully updated!",
                    HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        } catch (DuplicateException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT
                    )
            );
        }
    }

    @PutMapping(value = "${department.update}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> updateDepartmentByCode(@RequestParam String departmentCode, @RequestBody DepartmentDTO departmentDTO) {
        try {
            ValidateFields.validateSingleField(departmentCode);

            departmentService.validateDepartmentFields(departmentDTO);

            Department departmentMappedForService = modelMapperDepartment.mapToEntity(departmentDTO);

            Department department = departmentService.updateDepartmentByCode(departmentCode, departmentMappedForService);

            DepartmentDTO departmentDTO2 = modelMapperDepartment.mapToDTO(department);

            GenericResponse<DepartmentDTO> response = GenericResponse.success(
                    departmentDTO2,
                    "Success: Department with code: " + departmentCode + " has been successfully updated!",
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
        } catch (DuplicateException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT
                    )
            );
        }
    }

    @DeleteMapping(value = "${department.delete}")
    public ResponseEntity<GenericResponse<DepartmentDTO>> deleteDepartmentByCode(@RequestParam String departmentCode) {
        try {
            ValidateFields.validateSingleField(departmentCode);
            departmentService.deleteDepartmentByCode(departmentCode);
            GenericResponse<DepartmentDTO> response = GenericResponse.empty(
                    "Success: Department with code: " + departmentCode + " has been successfully deleted! ",
                    HttpStatus.OK
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping(value = "${department.find-employee-by-iddepartment}")
    public ResponseEntity<GenericResponse<List<EmployeeDepartmentDTO>>> getDepartmentEmployeesFindByCodeDepartment(@RequestParam String departmentCode) {

        try {
            List<EmployeeDepartmentDTO> employeeDepartmentDTOList = departmentService.getDepartmentEmployeesFindByCodeDepartment(departmentCode);
            GenericResponse<List<EmployeeDepartmentDTO>> response = GenericResponse.success(
                    employeeDepartmentDTOList,
                    "Success: This Department has " + employeeDepartmentDTOList.size() +
                            (employeeDepartmentDTOList.size() == 1 ? " employee." : " employees."),
                    HttpStatus.OK);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST
                    )
            );
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND
                    )
            );
        }
    }
}
