package com.fincons.service.employeeService.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Project;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.ProjectMapper;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.employeeService.ProjectService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public ResponseEntity<Object> getProjectById(long id) {
        Project existingProject = validateProjectById(id);
        ProjectDTO projectDTO = projectMapper.mapProject(existingProject);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found project with ID " + id + ".",
                (HttpStatus.OK),
                projectDTO);
    }

    @Override
    public ResponseEntity<Object> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> newListProject = new ArrayList<>();
        //Check if the list of projects is empty
        for (Project project : projects) {
            if (project != null) {
                ProjectDTO projectsDTO = projectMapper.mapProject(project);
                newListProject.add(projectsDTO);
            } else {
                throw new IllegalArgumentException("There aren't Project");
            }
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found " + newListProject.size() +
                        (newListProject.size() == 1 ? " project" : " projects") + " in the list.",
                (HttpStatus.OK),
                newListProject);
    }

    @Override
    public ResponseEntity<Object> createProject(Project project) {

        //Condition for not have null attributes
        validateProjectFields(project);

        List<Project> projects = projectRepository.findAll();
        //Condition if there are projects with name same
        checkForDuplicateProject(project, projects);

        ProjectDTO projectDTO = projectMapper.mapProjectWithoutEmployees(project);
        projectRepository.save(project);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Project with ID "+ project.getId() +" has been successfully updated!",
                (HttpStatus.OK), projectDTO);
    }

    @Override
    public ResponseEntity<Object> updateProjectById(long id, Project project) {
        

        //Condition for not have null attributes
        validateProjectFields(project);

        ProjectDTO projectDTO;
        //Check if the specified ID exists
        Project existingProject = validateProjectById(id);

        List<Project> projects = projectRepository.findAll();

        existingProject.setId(id);
        existingProject.setName(project.getName());
        existingProject.setArea(project.getArea());
        existingProject.setPriority(project.getPriority());

        List<Project> projectstWithoutProjectIdChosed = new ArrayList<>();

        for ( Project p : projects ) {
            if(p.getId() != id){
                projectstWithoutProjectIdChosed.add(p);
            }
        }
        if(projectstWithoutProjectIdChosed.isEmpty()){
            projectRepository.save(existingProject);
        }
        for (Project p : projectstWithoutProjectIdChosed) {
            if(p.getName().equals(existingProject.getName())
            ){
                throw new IllegalArgumentException("The project existing yet");
            }else{
                projectRepository.save(existingProject);
            }
        }


        projectRepository.save(existingProject);
                
        projectDTO = projectMapper.mapProjectWithoutEmployees(project);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Project with ID "+ id +" has been successfully updated!",
                (HttpStatus.OK),
                projectDTO);
    }

    @Override
    public ResponseEntity<Object> deleteProjectById(long id) {

        getProjectById(id);
        projectRepository.deleteById(id);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Project with ID "+ id +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    public Project validateProjectById(long id){
        Project existingProject = projectRepository.findById(id);
        if (existingProject == null){
            throw new ResourceNotFoundException("Project with ID: " + id + " not found");
        }
        return existingProject;
    }

    private void validateProjectFields(Project project){
        //If one field is true run Exception
        if (Strings.isEmpty(project.getName())||
                Strings.isEmpty(project.getArea()) ||
                Strings.isEmpty(project.getPriority())) {
            throw new IllegalArgumentException("The fields of the Project can't be null or empty");
        }

    }
    private void checkForDuplicateProject(Project project, List<Project> projects){
        for (Project project1 : projects){
            if(project1.getName().equals(project.getName())){
                throw new IllegalArgumentException("Project with the same name, already exists");
            }
        }
    }
}
