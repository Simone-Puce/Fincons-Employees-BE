package com.fincons.controller;

import com.fincons.Handler.GenericResponse;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.exception.DuplicateNameException;
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
                    "Success: Found project with ID " + projectId + ".",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value())
            );
        }
    }

    @GetMapping(value = "${project.list}")
    public ResponseEntity<Object> getAllProjects() {
        try {

            List<Project> projects = projectService.getAllProjects();

            List<ProjectDTO> projectDTOs = new ArrayList<>();
            for (Project project : projects) {
                ProjectDTO projectDTO = modelMapperProject.mapToDTO(project);
                projectDTOs.add(projectDTO);
            }
            GenericResponse<List<ProjectDTO>> response = GenericResponse.success(
                    projectDTOs,
                    "Success: Found " + projectDTOs.size() +
                            (projectDTOs.size() == 1 ? " project" : " projects" + "."),
                    HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException iax){
            return ResponseEntity.status(200).body(
                    GenericResponse.empty(
                            "There aren't Projects",
                            HttpStatus.NOT_FOUND.value()));
        }
    }

    @PostMapping(value = "${project.create}")
    public ResponseEntity<GenericResponse<ProjectDTO>> createProject(@RequestBody ProjectDTO projectDTO) {
        try {
            Project project = projectService.createProject(projectDTO);

            ProjectDTO projectDTO2 = modelMapperProject.mapToDTO(project);

            GenericResponse<ProjectDTO> response = GenericResponse.success(
                    projectDTO2,
                    "Success: Project with ID " + project.getProjectId() + " has been successfully updated!",
                    HttpStatus.OK.value());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            "The fields of the Project can't be null or empty",
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        }
        catch(DuplicateNameException dne){
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            "Project with the same name, already exists",
                            HttpStatus.CONFLICT.value()
                    )
            );
        }
    }
    @PutMapping(value = "${project.update}")
    public ResponseEntity<Object> updateProjectById(@RequestParam String projectId, @RequestBody ProjectDTO projectDTO) {
        try{
            Project project = projectService.updateProjectById(projectId, projectDTO);

            ProjectDTO projectDTO2 = modelMapperProject.mapToDTO(project);

            GenericResponse<ProjectDTO> response = GenericResponse.success(
                    projectDTO2,
                    "Success: Project with ID "+ projectId +" has been successfully updated!",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
        catch(IllegalArgumentException iae){
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            "The fields of the Project can't be null or empty",
                            HttpStatus.BAD_REQUEST.value()
                    )
            );
        }
        catch(DuplicateNameException dne){
            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            "The project existing yet",
                            HttpStatus.CONFLICT.value()
                    )
            );
        }
    }

    @DeleteMapping(value = "${project.delete}")
    public ResponseEntity<Object> deleteProjectById(@RequestParam String projectId) {
        try{
            projectService.deleteProjectById(projectId);
            GenericResponse<ProjectDTO> response = GenericResponse.empty(
                    "Success: project with ID " + projectId+ " has been successfully deleted! ",
                    HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException rnfe){

            return ResponseEntity.status(200).body(
                    GenericResponse.error(
                            rnfe.getMessage(),
                            HttpStatus.NOT_FOUND.value()));
        }
    }
}
