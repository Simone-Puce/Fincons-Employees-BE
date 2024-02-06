package com.fincons.controller;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.mapper.ProjectMapper;
import com.fincons.utility.GenericResponse;
import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.DuplicateNameException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.service.employeeService.EmployeeService;
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
    private EmployeeMapper modelMapperEmployee;

    @Autowired
    private ProjectMapper projectMapper;

    @GetMapping(value = "/find-by-id")
    public ResponseEntity<GenericResponse<EmployeeDTO>> getEmployeeById(@RequestParam String employeeId) {
        try {
            Employee employee = employeeService.getEmployeeById(employeeId);
            EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO,
                    "Success: Found Employee with ID " + employeeId + ".",
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
                            HttpStatus.NOT_FOUND.value())
            );
        }

    }

    @GetMapping(value = "/find-by-email")
    public ResponseEntity<GenericResponse<EmployeeDTO>> getDepartmentByEmail(@RequestParam String email) {
        try {
            Employee employee = employeeService.getEmployeeByEmail(email);
            EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO,
                    "Success: Found Employee with email " + email + ".",
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
                            HttpStatus.NOT_FOUND.value())
            );
        }
    }

    @GetMapping(value = "/list")
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
                HttpStatus.OK.value());
        return ResponseEntity.ok(response);

    }

    @PostMapping(value = "/create")
    public ResponseEntity<GenericResponse<EmployeeDTO>> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
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
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()));
        }
        catch (IllegalArgumentException iae) {
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

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateEmployeeById(@RequestParam String employeeId, @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeService.updateEmployeeById(employeeId, employeeDTO);
            EmployeeDTO employeeDTO2 = modelMapperEmployee.mapToDTO(employee);

            GenericResponse<EmployeeDTO> response = GenericResponse.success(
                    employeeDTO2,
                    "Success: Employee with ID " + employeeId + " has been successfully updated!",
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
            return ResponseEntity.status(200).body(
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

    @DeleteMapping(value = "/delete")
    public ResponseEntity<GenericResponse<EmployeeDTO>> deleteEmployeeById(@RequestParam String employeeId) {
        try {
            employeeService.deleteEmployeeById(employeeId);
            GenericResponse<EmployeeDTO> response = GenericResponse.empty(
                    "Success: Department with ID " + employeeId + " has been successfully deleted! ",
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

    @GetMapping(value = "/find/employee-project")
    public ResponseEntity<GenericResponse<List<ProjectDTO>>> getAllEmployeesProjects(@RequestParam String employeeId) {
        try {
            List<Project> projects = employeeService.findAllEmployeeProjects(employeeId);

            List<ProjectDTO> projectsDTO = new ArrayList<>();

            for (Project project : projects) {
                ProjectDTO projectDTO = projectMapper.mapToDTO(project);
                projectsDTO.add(projectDTO);
            }
            GenericResponse<List<ProjectDTO>> response = GenericResponse.success(
                    projectsDTO,
                    "Success: This Employee works in " + projectsDTO.size() +
                            (projectsDTO.size() == 1 ? " project." : " projects."),
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()));
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            iae.getMessage(),
                            HttpStatus.NO_CONTENT.value()
                    )
            );
        }
    }

    @GetMapping(value = "/list/employee-project")
    public ResponseEntity<GenericResponse<List<EmployeeProjectDTO>>> getAllEmployeeProject() {

        List<EmployeeProjectDTO> employeeProjectDTO = employeeService.getAllEmployeeProject();

        GenericResponse<List<EmployeeProjectDTO>> response = GenericResponse.success(
                employeeProjectDTO,
                "Success: " + (employeeProjectDTO.isEmpty() || employeeProjectDTO.size() == 1 ? "Found " : "Founds ") + employeeProjectDTO.size() +
                        (employeeProjectDTO.isEmpty() || employeeProjectDTO.size() == 1 ? " relationship " : " relationships ")+ "in the search.",
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create/employee-project")
    public ResponseEntity<GenericResponse<EmployeeProjectDTO>> createEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId) {
        try {
            EmployeeProjectDTO employeeProjectDTO = employeeService.addEmployeeProject(employeeId, projectId);

            GenericResponse<EmployeeProjectDTO> response = GenericResponse.success(
                    employeeProjectDTO,
                    "Success: Addition of relationship between employee with ID: " + employeeId + " and project with ID: " + projectId,
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            iae.getMessage(),
                            HttpStatus.NO_CONTENT.value()
                    )
            );
        }
        catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()));
        } catch (DuplicateNameException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT.value()
                    )
            );
        }
    }

    @PutMapping(value = "/update/employee-project")
    public ResponseEntity<GenericResponse<EmployeeProjectDTO>> updateEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId, @RequestBody EmployeeProjectDTO employeeProjectDTO) {
        try {
            EmployeeProjectDTO employeeProjectDTO1 = employeeService.updateEmployeeProject(employeeId, projectId, employeeProjectDTO);
            GenericResponse<EmployeeProjectDTO> response = GenericResponse.success(
                    employeeProjectDTO,
                    "Success: Relationship updated between employee with ID " + employeeId + " and project with ID " + projectId + ". " +
                            "Updated details for employee with ID " + employeeProjectDTO1.getEmployeeId() + " and project with ID " + employeeProjectDTO1.getProjectId() + ".",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | ResourceNotFoundException iae) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            iae.getMessage(),
                            HttpStatus.NOT_FOUND.value())
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

    @DeleteMapping(value = "/delete/employee-project")
    public ResponseEntity<GenericResponse<EmployeeProjectDTO>> deleteEmployeeProject(@RequestParam String employeeId, @RequestParam String projectId) {
        try {
            employeeService.deleteEmployeeProject(employeeId, projectId);
            GenericResponse<EmployeeProjectDTO> response = GenericResponse.empty(
                    "Success: Relationship deleted between employee with ID " + employeeId + " and project with ID " + projectId + ".",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);

        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.ok(
                    GenericResponse.empty(
                            iae.getMessage(),
                            HttpStatus.NO_CONTENT.value()
                    )
            );
        }catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NO_CONTENT.value()
                    )
            );
        }
    }

}

