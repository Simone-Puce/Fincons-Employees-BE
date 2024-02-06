package com.fincons.service.employeeService.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Project;
import com.fincons.exception.DuplicateNameException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.ProjectMapper;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.employeeService.ProjectService;
import com.fincons.utility.ValidateSingleField;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.Validate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectMapper modelMapperProject;

    @Override
    public Project getProjectById(String projectId) {
        ValidateSingleField.validateSingleField(projectId);
        return validateProjectById(projectId);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(ProjectDTO projectDTO) {

        //Condition for not have null attributes
        validateProjectFields(projectDTO);

        List<Project> projects = projectRepository.findAll();
        //Condition if there are projects with name same
        checkForDuplicateProject(projectDTO, projects);

        Project project = modelMapperProject.mapToEntity(projectDTO);

        projectRepository.save(project);
        
        return project;
    }

    @Override
    public Project updateProjectById(String projectId, ProjectDTO projectDTO) {
        
        //Condition for not have null attributes
        ValidateSingleField.validateSingleField(projectId);
        validateProjectFields(projectDTO);

        List<Project> projects = projectRepository.findAll();

        //Check if the specified ID exists
        Project project = validateProjectById(projectId);

        project.setProjectId(projectId);
        project.setName(projectDTO.getName());
        project.setArea(projectDTO.getArea());
        project.setPriority(projectDTO.getPriority());

        List<Project> projectstWithoutProjectIdChosed = new ArrayList<>();

        for (Project p : projects ) {
            if(!Objects.equals(p.getProjectId(), projectId)){
                projectstWithoutProjectIdChosed.add(p);
            }
        }
        if(projectstWithoutProjectIdChosed.isEmpty()){
            projectRepository.save(project);
        }
        else {
            for (Project p : projectstWithoutProjectIdChosed) {
                if (p.getName().equals(project.getName())
                ) {
                    throw new IllegalArgumentException("The project existing yet");
                } else {
                    projectRepository.save(project);
                    break;
                }
            }
        }

        return project;
    }

    @Override
    public void deleteProjectById(String projectId) {

        ValidateSingleField.validateSingleField(projectId);
        Project project = validateProjectById(projectId);
        projectRepository.deleteById(project.getId());
    }

    public Project validateProjectById(String projectId){
        Project project = projectRepository.findProjectByProjectId(projectId);
        if (project == null){
            throw new ResourceNotFoundException("Project with ID: " + projectId + " not found");
        }
        return project;
    }

    private void validateProjectFields(ProjectDTO projectDTO){
        //If one field is true run Exception
        if (Strings.isEmpty(projectDTO.getName())||
                Strings.isEmpty(projectDTO.getArea()) ||
                Strings.isEmpty(projectDTO.getPriority())) {
            throw new IllegalArgumentException("The fields of the Project can't be null or empty");
        }

    }
    private void checkForDuplicateProject(ProjectDTO projectDTO, List<Project> projects){
        for (Project project1 : projects){
            if(project1.getName().equals(projectDTO.getName())){
                throw new DuplicateNameException("Project with the same name, already exists");
            }
        }
    }
}
