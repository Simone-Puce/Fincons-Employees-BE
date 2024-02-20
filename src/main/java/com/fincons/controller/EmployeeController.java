package com.fincons.controller;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.mapper.ProjectMapper;
import com.fincons.dto.ImportResultDTO;
import com.fincons.service.employeeService.impl.EmployeeServiceImpl;
import com.fincons.utility.GenericResponse;
import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.enums.ProcessingStatus;
import com.fincons.exception.EmailException;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.importFile.ImportService;
import com.fincons.utility.ValidateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/company-employee-management")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper modelMapperEmployee;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ImportService importService;

    @GetMapping(value = "${employee.find-by-ssn}")
    public ResponseEntity<GenericResponse<EmployeeDTO>> getEmployeeBySsn(@RequestParam String ssn) {
        try {
            ValidateFields.validateSingleField(ssn);
            Employee employee = employeeService.getEmployeeBySsn(ssn);
            EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO,
                    "Success: Found Employee with SSN " + ssn + ".",
                    HttpStatus.OK
            );
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

    @GetMapping(value = "${employee.find-by-email}")
    public ResponseEntity<GenericResponse<EmployeeDTO>> getDepartmentByEmail(@RequestParam String email) {
        try {
            ValidateFields.validateSingleField(email);
            Employee employee = employeeService.getEmployeeByEmail(email);
            EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO,
                    "Success: Found Employee with email " + email + ".",
                    HttpStatus.OK
            );
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

    @GetMapping(value = "${employee.list}")
    public ResponseEntity<Object> getAllEmployees() {

        List<Employee> employees = employeeService.getAllEmployees();

        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(employee);
            employeeDTOs.add(employeeDTO);
        }
        GenericResponse<List<EmployeeDTO>> response = GenericResponse.success(
                employeeDTOs,
                "Success:" + (employeeDTOs.isEmpty() || employeeDTOs.size() == 1 ? " Found " : " Founds ") + employeeDTOs.size() +
                        (employeeDTOs.isEmpty() || employeeDTOs.size() == 1 ? " employee" : " employees") + ".",
                HttpStatus.OK);
        return ResponseEntity.ok(response);

    }

    @PostMapping(value = "${employee.create}")
    public ResponseEntity<GenericResponse<EmployeeDTO>> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.validateEmployeeFields(employeeDTO);
            employeeService.validateGender(employeeDTO.getGender());

            Employee employeeMapped = modelMapperEmployee.mapToEntity(employeeDTO);

            Employee employee = employeeService.createEmployee(employeeMapped);

            EmployeeDTO employeeDTO2 = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO2,
                    "Success: Employee with SSN " + employee.getSsn() + " has been successfully updated!",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        }
        //This Exception assert if Department or Position exist
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND));
        }
        catch (IllegalArgumentException iae) {
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

    @PutMapping(value = "${employee.update}")
    public ResponseEntity<Object> updateEmployeeBySsn(@RequestParam String ssn, @RequestBody EmployeeDTO employeeDTO) {
        try {
            ValidateFields.validateSingleField(ssn);
            employeeService.validateEmployeeFields(employeeDTO);
            employeeService.validateGender(employeeDTO.getGender());

            Employee employeeMappedForService = modelMapperEmployee.mapToEntity(employeeDTO);

            Employee employee = employeeService.updateEmployeeBySsn(ssn, employeeMappedForService);

            EmployeeDTO employeeDTO2 = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO2,
                    "Success: Employee with SSN " + ssn + " has been successfully updated!",
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
            return ResponseEntity.status(200).body(
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

    @DeleteMapping(value = "${employee.delete}")
    public ResponseEntity<GenericResponse<EmployeeDTO>> deleteEmployeeBySsn(@RequestParam String ssn) {
        try {
            ValidateFields.validateSingleField(ssn);
            employeeService.deleteEmployeeBySsn(ssn);
            GenericResponse<EmployeeDTO> response = GenericResponse.empty(
                    "Success: Department with SSN " + ssn + " has been successfully deleted! ",
                    HttpStatus.OK
            );
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

    @GetMapping(value = "${employee.find-employee-projects}")
    public ResponseEntity<GenericResponse<List<ProjectDTO>>> getAllEmployeeProjects(
            @RequestParam(value = "ssn") String ssn
    ) {
        try {
            ValidateFields.validateSingleField(ssn);
            List<Project> projects = employeeService.findAllEmployeeProjects(ssn);

            List<ProjectDTO> projectsDTO = new ArrayList<>();

            for (Project project : projects) {
                ProjectDTO projectDTO = projectMapper.mapToDTO(project);
                projectsDTO.add(projectDTO);
            }
            GenericResponse<List<ProjectDTO>> response = GenericResponse.success(
                    projectsDTO,
                    "Success: This Employee works in " + projectsDTO.size() +
                            (projectsDTO.size() == 1 ? " project." : " projects."),
                    HttpStatus.OK
            );

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            iae.getMessage(),
                            HttpStatus.NO_CONTENT
                    )
            );
        }
    }

    @GetMapping(value = "${employee.list-employees-projects}")
    public ResponseEntity<GenericResponse<List<EmployeeProjectDTO>>> getAllEmployeeProject() {

        List<EmployeeProjectDTO> employeeProjectDTO = employeeService.getAllEmployeeProject();

        GenericResponse<List<EmployeeProjectDTO>> response = GenericResponse.success(
                employeeProjectDTO,
                "Success: " + (employeeProjectDTO.isEmpty() || employeeProjectDTO.size() == 1 ? "Found " : "Founds ") + employeeProjectDTO.size() +
                        (employeeProjectDTO.isEmpty() || employeeProjectDTO.size() == 1 ? " relationship " : " relationships ")+ "in the search.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "${employee.create-employee-project}")
    public ResponseEntity<GenericResponse<EmployeeProjectDTO>> createEmployeeProject(@RequestParam String ssn, @RequestParam String projectId) {
        try {
            EmployeeProjectDTO employeeProjectDTO = employeeService.addEmployeeProject(ssn, projectId);

            GenericResponse<EmployeeProjectDTO> response = GenericResponse.success(
                    employeeProjectDTO,
                    "Success: Addition of relationship between employee with SSN: " + ssn + " and project with ID: " + projectId,
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            iae.getMessage(),
                            HttpStatus.NO_CONTENT
                    )
            );
        }
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND));
        } catch (DuplicateException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT
                    )
            );
        }
    }

    @PutMapping(value = "${employee.update-employee-project}")
    public ResponseEntity<GenericResponse<EmployeeProjectDTO>> updateEmployeeProject(@RequestParam String ssn, @RequestParam String projectId, @RequestBody EmployeeProjectDTO employeeProjectDTO) {
        try {
            EmployeeProjectDTO employeeProjectDTO1 = employeeService.updateEmployeeProject(ssn, projectId, employeeProjectDTO);
            GenericResponse<EmployeeProjectDTO> response = GenericResponse.success(
                    employeeProjectDTO,
                    "Success: Relationship updated between employee with SSN " + ssn + " and project with ID " + projectId + ". " +
                            "Updated details for employee with ID " + employeeProjectDTO1.getSsn() + " and project with ID " + employeeProjectDTO1.getProjectId() + ".",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | ResourceNotFoundException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.NOT_FOUND)
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

    @DeleteMapping(value = "${employee.delete-employee-project}")
    public ResponseEntity<GenericResponse<EmployeeProjectDTO>> deleteEmployeeProject(@RequestParam String ssn, @RequestParam String projectId) {
        try {
            employeeService.deleteEmployeeProject(ssn, projectId);
            GenericResponse<EmployeeProjectDTO> response = GenericResponse.empty(
                    "Success: Relationship deleted between employee with SSN " + ssn + " and project with ID " + projectId + ".",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);

        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            iae.getMessage(),
                            HttpStatus.NO_CONTENT
                    )
            );
        }catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NO_CONTENT
                    )
            );
        }
    }

    @PostMapping("${employee.importfile}")
    public ResponseEntity<ImportResultDTO> addEmployeeFromFile(@RequestBody MultipartFile importedFile) {

            ImportResultDTO importResult = importService.processImport(importedFile);
            if (importResult.getStatus() == ProcessingStatus.NOT_LOADED
                    || importResult.getStatus()== ProcessingStatus.LOADED_WITH_ERRORS
                    || importResult.getStatus()== ProcessingStatus.LOADED) {
                return ResponseEntity.ok(importResult);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(importResult);
            }







    }



}

