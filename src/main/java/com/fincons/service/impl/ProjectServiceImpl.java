package com.fincons.service.impl;

import com.fincons.entity.Project;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.EmployeeProjectService;
import com.fincons.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService, EmployeeProjectService {

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
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}
