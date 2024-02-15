package com.fincons.service.employeeService.impl;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.ProjectMapper;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.employeeService.ProjectService;
import com.fincons.utility.ValidateFields;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project getProjectById(String projectId) {
        return validateProjectById(projectId);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project) {

        checkForDuplicateProject(project.getName());

        projectRepository.save(project);

        return project;
    }

    @Override
    public Project updateProjectById(String projectId, Project project) {

        List<Project> projects = projectRepository.findAll();

        //Check if the specified ID exists
        Project projectExisting = validateProjectById(projectId);

        List<Project> projectsWithoutProjectIdChosed = new ArrayList<>();

        for (Project p : projects) {
            if (!Objects.equals(p.getProjectId(), projectId)) {
                projectsWithoutProjectIdChosed.add(p);
            }
        }

        //If it doesn't need to change the UUID, set projectId to the string projectId in the method signature
        projectExisting.setProjectId(project.getProjectId());
        projectExisting.setName(project.getName());
        projectExisting.setArea(project.getArea());
        projectExisting.setPriority(project.getPriority());

        if (projectsWithoutProjectIdChosed.isEmpty()) {
            projectRepository.save(projectExisting);
        } else {
            for (Project p : projectsWithoutProjectIdChosed) {
                if (p.getName().equals(projectExisting.getName())
                ) {
                    throw new IllegalArgumentException("The project existing yet");
                }
            }
            projectRepository.save(projectExisting);
        }
        return projectExisting;
    }

    @Override
    public void deleteProjectById(String projectId) {
        Project project = validateProjectById(projectId);
        projectRepository.deleteById(project.getId());
    }

    public Project validateProjectById(String projectId) {
        Project project = projectRepository.findProjectByProjectId(projectId);
        if (project == null) {
            throw new ResourceNotFoundException("Project with ID: " + projectId + " not found");
        }
        return project;
    }

    public void validateProjectFields(ProjectDTO projectDTO) {
        //If one field is true run Exception
        if (Strings.isEmpty(projectDTO.getName()) ||
                Strings.isEmpty(projectDTO.getArea()) ||
                Strings.isEmpty(projectDTO.getPriority())) {
            throw new IllegalArgumentException("The fields of the Project can't be null or empty");
        }

    }

    private void checkForDuplicateProject(String projectName) {
        Project projectByName = projectRepository.findProjectByName(projectName);
        if (projectByName != null) {
            throw new DuplicateException("Project with the same name, already exists");
        }
    }
}
