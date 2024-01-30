package com.fincons.service.employeeService;


import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    Project getProjectById(String projectId);
    List<Project> getAllProjects();
    Project createProject(ProjectDTO projectDTO);
    Project updateProjectById(String projectId, ProjectDTO projectDTO);
    void deleteProjectById(String projectId);

}
