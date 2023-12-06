package com.fincons.controller;

import com.fincons.entity.Project;
import com.fincons.entity.Project;
import com.fincons.entity.Project;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping(value = "/find")
    public Project getProjectById(@RequestParam long id){
        return projectService.findById(id);
    }

    @GetMapping(value="/list")
    public List<Project> getAllProjects(){
        return projectService.findAll();
    }
    @PostMapping(value = "/create")
    public Project createProject(@RequestBody Project project){
        return projectService.saveProject(project);
    }
    
    
}