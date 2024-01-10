package com.fincons.controller;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${project.uri}")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping(value = "/find")
    public ResponseEntity<Object> getProjectById(@RequestParam long id){
        return projectService.findById(id);
    }

    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllProjects(){
        return projectService.findAll();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createProject(@RequestBody Project project){
        return projectService.save(project);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateProjectById(@RequestParam long id, @RequestBody Project project){
        return projectService.update(id, project);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteProjectById(@RequestParam long id){
        return projectService.deleteById(id);
    }
    
    
}
