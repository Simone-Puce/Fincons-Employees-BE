package com.fincons.service.impl;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.ProjectMapper;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public ResponseEntity<ProjectDTO> findById(long id) {
        Project existingProject = getProjectById(id);
        //Check if project not found
        ProjectDTO projectDTO = projectMapper.mapProject(existingProject);
        return ResponseEntity.ok(projectDTO);
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> findAll() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> newListProject = new ArrayList<>();
        //Check if the list of projects is empty
        for (Project project : projects) {
            if (project != null) {
                ProjectDTO projectsDTO = projectMapper.mapProjectWithoutEmployees(project);
                newListProject.add(projectsDTO);
            } else {
                throw new IllegalArgumentException("There aren't Project");
            }
        }
        return ResponseEntity.ok(newListProject);
    }

    @Override
    public ResponseEntity<ProjectDTO> save(Project project) {

        //Condition for not have null attributes
        validateProjectFields(project);

        List<Project> projects = projectRepository.findAll();
        //Condition if there are projects with name same
        checkForDuplicateProject(project, projects);

        ProjectDTO projectDTO = projectMapper.mapProjectWithoutEmployees(project);
        projectRepository.save(project);
        return ResponseEntity.ok(projectDTO);
    }

    @Override
    public ResponseEntity<ProjectDTO> update(long id, Project project) {
        
        ProjectDTO projectDTO;
        //Condition for not have null attributes
        validateProjectFields(project);

        //Condition for not have null Project
        Project existingProject = getProjectById(id);

        existingProject.setName(project.getName());
        existingProject.setArea(project.getArea());
        existingProject.setPriority(project.getPriority());
        projectRepository.save(existingProject);
                
        projectDTO = projectMapper.mapProjectWithoutEmployees(project);

        return ResponseEntity.ok(projectDTO);
    }

    @Override
    public ResponseEntity<ProjectDTO> deleteById(long id) {

        getProjectById(id);
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public Project getProjectById(long id){
        Project existingProject = projectRepository.findById(id);
        if (existingProject == null){
            throw new ResourceNotFoundException("Project with ID: " + id + " not found");
        }
        return existingProject;
    }

    private void validateProjectFields(Project project){
        //If one field is true run Exception
        if (project.getName() == null || project.getName().isEmpty() ||
                project.getArea() == null || project.getArea().isEmpty() ||
                project.getPriority() == null || project.getPriority().isEmpty()) {
            throw new IllegalArgumentException("The fields of the Project can't be null or empty");
        }

    }
    private void checkForDuplicateProject(Project project, List<Project> projects){
        for (Project project1 : projects){
            if(project1.getName().equals(project.getName())){
                throw new IllegalArgumentException("The names can't be same");
            }
        }
    }
}
