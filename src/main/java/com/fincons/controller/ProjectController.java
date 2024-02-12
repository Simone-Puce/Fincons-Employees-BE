package com.fincons.controller;

import com.fincons.utility.GenericResponse;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.ProjectMapper;
import com.fincons.service.employeeService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/company-employee-management")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper modelMapperProject;

    @GetMapping(value = "${project.find-project-by-id}")
    public ResponseEntity<Object> getProjectById(@RequestParam String projectId) {
        try {
            Project project = projectService.getProjectById(projectId);
            ProjectDTO projectDTO = modelMapperProject.mapToDTO(project);

            GenericResponse<ProjectDTO> response = GenericResponse.success(
                    projectDTO,
                    "Success: Found project with id: " + projectId + ".",
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

    @GetMapping(value = "${project.list}")
    public ResponseEntity<Object> getAllProjects() {


        List<Project> projects = projectService.getAllProjects();

        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Project project : projects) {
            ProjectDTO projectDTO = modelMapperProject.mapToDTO(project);
            projectDTOs.add(projectDTO);
        }
        GenericResponse<List<ProjectDTO>> response = GenericResponse.success(
                projectDTOs,
                "Success: " + (projectDTOs.isEmpty() || projectDTOs.size() == 1 ? "Found " : "Founds ") + projectDTOs.size() +
                        (projectDTOs.isEmpty() || projectDTOs.size() == 1 ? " project" : " projects") + ".",
                HttpStatus.OK);
        return ResponseEntity.ok(response);


    }

    @PostMapping(value = "${project.create}")
    public ResponseEntity<GenericResponse<ProjectDTO>> createProject(@RequestBody ProjectDTO projectDTO) {
        try {
            Project project = projectService.createProject(projectDTO);

            ProjectDTO projectDTO2 = modelMapperProject.mapToDTO(project);

            GenericResponse<ProjectDTO> response = GenericResponse.success(
                    projectDTO2,
                    "Success: Project with id: " + project.getProjectId() + " has been successfully updated!",
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
        } catch (DuplicateException dne) {
            return ResponseEntity.ok(
                    GenericResponse.error(
                            dne.getMessage(),
                            HttpStatus.CONFLICT
                    )
            );
        }
    }


    @PutMapping(value = "${project.update}")
    public ResponseEntity<Object> updateProjectById(@RequestParam String projectId, @RequestBody ProjectDTO projectDTO) {
        try {
            Project project = projectService.updateProjectById(projectId, projectDTO);

            ProjectDTO projectDTO2 = modelMapperProject.mapToDTO(project);

            GenericResponse<ProjectDTO> response = GenericResponse.success(
                    projectDTO2,
                    "Success: Project with id: " + projectId + " has been successfully updated!",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        }
        catch (ResourceNotFoundException rfe) {
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            rfe.getMessage(),
                            HttpStatus.NOT_FOUND
                    )
            );
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

    @DeleteMapping(value = "${project.delete}")
    public ResponseEntity<Object> deleteProjectById(@RequestParam String projectId) {
        try {
            projectService.deleteProjectById(projectId);
            GenericResponse<ProjectDTO> response = GenericResponse.empty(
                    "Success: project with id: " + projectId + " has been successfully deleted! ",
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
