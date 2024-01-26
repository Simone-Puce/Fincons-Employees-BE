package com.fincons.controller;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.service.employeeService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company-employee-management")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping(value = "${project.find-project-by-id}")
    public ResponseEntity<Object> getProjectById(@RequestParam String projectId){
        return projectService.getProjectById(projectId);
    }

    @GetMapping(value="${project.list}")
    public ResponseEntity<Object> getAllProjects(){
        return projectService.getAllProjects();
    }
    @PostMapping(value = "${project.create}")
    public ResponseEntity<Object> createProject(@RequestBody ProjectDTO projectDTO){
        return projectService.createProject(projectDTO);
    }
    @PutMapping(value = "${project.update}")
    public ResponseEntity<Object> updateProjectById(@RequestParam String projectId, @RequestBody ProjectDTO projectDTO) {
        return projectService.updateProjectById(projectId, projectDTO);
    }
    @DeleteMapping(value = "${project.delete}")
    public ResponseEntity<Object> deleteProjectById(@RequestParam String projectId){
        return projectService.deleteProjectById(projectId);
    }
    
    
}
