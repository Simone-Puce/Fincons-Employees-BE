package com.fincons.controller;

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
    public ResponseEntity<Object> getProjectById(@RequestParam long id){
        return projectService.getProjectById(id);
    }

    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllProjects(){
        return projectService.getAllProjects();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateProjectById(@RequestParam long id, @RequestBody Project project) throws Exception {
        return projectService.updateProjectById(id, project);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteProjectById(@RequestParam long id){
        return projectService.deleteProjectById(id);
    }
    
    
}