package com.fincons.service.impl;

import com.fincons.entity.Project;
import com.fincons.entity.Project;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project findById(long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(long id, Project project) {
        Project existingProject = projectRepository.findById(id);
        if (existingProject == null) {
            throw new ResourceNotFoundException("Project with ID: " + id + " not found");
        } else {
            existingProject.setName(project.getName());
            existingProject.setArea(project.getArea());
            existingProject.setPriority(project.getPriority());
        }
        return projectRepository.save(existingProject);
    }
    

    @Override
    public void deleteById(long id) {
        projectRepository.deleteById(id);
    }
}
