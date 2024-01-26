package com.fincons.controller;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.service.employeeService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${project.uri}")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping(value = "/find-by-id")
    public ResponseEntity<Object> getProjectById(@RequestParam String projectId){
        return projectService.getProjectById(projectId);
    }

    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllProjects(){
        return projectService.getAllProjects();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createProject(@RequestBody ProjectDTO projectDTO){
        return projectService.createProject(projectDTO);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateProjectById(@RequestParam String projectId, @RequestBody ProjectDTO projectDTO) {
        return projectService.updateProjectById(projectId, projectDTO);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteProjectById(@RequestParam String projectId){
        return projectService.deleteProjectById(projectId);
    }
    
    
}
