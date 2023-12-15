package com.fincons.controller;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.entity.Project;
import com.fincons.entity.Project;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${project.uri}")
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
        return projectService.save(project);
    }
    @PutMapping(value = "/update")
    public Project updateProjectById(@RequestParam long id, @RequestBody Project project){
        return projectService.update(id, project);
    }
    @DeleteMapping(value = "/delete")
    public void deleteProjectById(@RequestParam long id){
        projectService.deleteById(id);
    }
    
    
}
