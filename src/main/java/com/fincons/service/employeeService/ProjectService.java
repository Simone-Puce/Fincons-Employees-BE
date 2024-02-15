package com.fincons.service.employeeService;


import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectService {
    Project getProjectById(String projectId);
    List<Project> getAllProjects();
    Project createProject(Project project);
    Project updateProjectById(String projectId, Project project);
    void deleteProjectById(String projectId);
    void validateProjectFields(ProjectDTO projectDTO);
}
