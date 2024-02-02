package com.fincons.controller;

import com.fincons.utility.GenericResponse;
import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.DuplicateNameException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.employeeService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${employee.uri}")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeMapper modelMapperEmployee;

    @GetMapping(value = "/find-by-id")
    public ResponseEntity<GenericResponse<EmployeeDTO>> getEmployeeById(@RequestParam String employeeId){
        try{
            Employee employee = employeeService.getEmployeeById(employeeId);
            EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTOWithFile(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO,
                    "Success: Found Employee with ID " + employeeId + ".",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
        catch (ResourceNotFoundException rnfe){
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value())
            );
        }

    }
    @GetMapping(value = "/find-by-email")
    public ResponseEntity<GenericResponse<EmployeeDTO>> getDepartmentByEmail(@RequestParam String email){
        try {
            Employee employee = employeeService.getEmployeeByEmail(email);
            EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTOWithFile(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO,
                    "Success: Found Employee with email " + email + ".",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
        catch (ResourceNotFoundException rnfe){
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value())
            );
        }
    }
    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllEmployees(){
        try{
            List<Employee> employees = employeeService.getAllEmployees();

            List<EmployeeDTO> employeeDTOs = new ArrayList<>();
            for (Employee employee: employees){
                EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(employee);
                employeeDTOs.add(employeeDTO);
            }
            GenericResponse<List<EmployeeDTO>> response = GenericResponse.success(
                    employeeDTOs,
                    "Success: Found " + employeeDTOs.size() +
                            (employeeDTOs.size() == 1 ? " employee" : " employees" + "."),
                    HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        }
        catch(IllegalArgumentException iax){
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            iax.getMessage(),
                            HttpStatus.NOT_FOUND.value()));
        }
    }
    @PostMapping(value = "/create")
    public ResponseEntity<GenericResponse<EmployeeDTO>> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        try {
            Employee employee = employeeService.createEmployee(employeeDTO);

            EmployeeDTO employeeDTO2 = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO2,
                    "Success: Employee with ID " + employee.getEmployeeId() + " has been successfully updated!",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException iae){
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        }
        catch (DuplicateNameException dne){
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT.value()
                    )
            );
        }
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateEmployeeById(@RequestParam String employeeId, @RequestBody EmployeeDTO employeeDTO) {
        try{
            Employee employee = employeeService.updateEmployeeById(employeeId, employeeDTO);
            EmployeeDTO employeeDTO2 = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO2,
                    "Success: Employee with ID "+ employeeId +" has been successfully updated!",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
        catch (ResourceNotFoundException rfe){
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            rfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()
                    )
            );
        }
        catch (IllegalArgumentException iae){
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        }
        catch(DuplicateNameException dne){
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT.value()
                    )
            );
        }
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<GenericResponse<EmployeeDTO>> deleteEmployeeById(@RequestParam String employeeId){
        try{
            employeeService.deleteEmployeeById(employeeId);
            GenericResponse<EmployeeDTO> response = GenericResponse.empty(
                    "Success: Department with ID " + employeeId+ " has been successfully deleted! ",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException rnfe){

            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping(value = "/find/employee-project")
    public ResponseEntity<Object> getAllEmployeesProjects(@RequestParam String employeeId){
        return employeeService.findAllEmployeeProjects(employeeId);
    }
    @GetMapping(value = "/list/employee-project")
    public ResponseEntity<Object> getAllEmployeeProject(){
        return employeeService.getAllEmployeeProject();
    }
    @PostMapping(value= "/create/employee-project")
    public ResponseEntity<Object> createEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId) {
        return employeeService.addEmployeeProject(employeeId, projectId);
    }
    @PutMapping(value ="/update/employee-project")
    public ResponseEntity<Object> updateEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId, @RequestBody EmployeeProjectDTO employeeProjectDTO){
        return employeeService.updateEmployeeProject(employeeId, projectId, employeeProjectDTO);
    }
    @DeleteMapping(value= "/delete/employee-project")
    public ResponseEntity<Object> deleteEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId) {
        return employeeService.deleteEmployeeProject(employeeId, projectId);
    }



}

