package com.fincons.service.employeeService.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.ProjectDTO;
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
import java.util.Objects;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectMapper modelMapperProject;



    @Override
    public ResponseEntity<Object> getProjectById(String idProject) {

        Project existingProject = validateProjectById(idProject);
        ProjectDTO projectDTO = modelMapperProject.mapToDTO(existingProject);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found project with ID " + idProject + ".",
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
                ProjectDTO projectsDTO = modelMapperProject.mapToDTO(project);
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
    public ResponseEntity<Object> createProject(ProjectDTO projectDTO) {

        //Condition for not have null attributes
        validateProjectFields(projectDTO);

        List<Project> projects = projectRepository.findAll();
        //Condition if there are projects with name same
        checkForDuplicateProject(projectDTO, projects);

        Project project = modelMapperProject.mapToEntity(projectDTO);

        projectRepository.save(project);

        projectDTO.setProjectId(project.getProjectId());

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Project with ID "+ project.getProjectId() +" has been successfully updated!",
                (HttpStatus.OK), projectDTO);
    }

    @Override
    public ResponseEntity<Object> updateProjectById(String idProject, ProjectDTO projectDTO) {
        

        //Condition for not have null attributes
        validateProjectFields(projectDTO);

        List<Project> projects = projectRepository.findAll();

        //Check if the specified ID exists
        Project existingProject = validateProjectById(idProject);

        existingProject.setProjectId(idProject);
        existingProject.setName(projectDTO.getName());
        existingProject.setArea(projectDTO.getArea());
        existingProject.setPriority(projectDTO.getPriority());

        List<Project> projectstWithoutProjectIdChosed = new ArrayList<>();

        for (Project p : projects ) {
            if(!Objects.equals(p.getProjectId(), idProject)){
                projectstWithoutProjectIdChosed.add(p);
            }
        }
        if(projectstWithoutProjectIdChosed.isEmpty()){
            projectRepository.save(existingProject);
            projectDTO.setProjectId(existingProject.getProjectId());
        }
        else {
            for (Project p : projectstWithoutProjectIdChosed) {
                if (p.getName().equals(existingProject.getName())
                ) {
                    throw new IllegalArgumentException("The project existing yet");
                } else {
                    projectRepository.save(existingProject);
                    projectDTO.setProjectId(existingProject.getProjectId());
                }
            }
        }

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Project with ID "+ idProject +" has been successfully updated!",
                (HttpStatus.OK),
                projectDTO);
    }

    @Override
    public ResponseEntity<Object> deleteProjectById(String idProject) {

        Project project = validateProjectById(idProject);
        projectRepository.deleteById(project.getId());
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Project with ID "+ idProject +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    public Project validateProjectById(String idProject){
        Project existingProject = projectRepository.findByProjectId(idProject);
        if (existingProject == null){
            throw new ResourceNotFoundException("Project with ID: " + idProject + " not found");
        }
        return existingProject;
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
                throw new IllegalArgumentException("Project with the same name, already exists");
            }
        }
    }
}
